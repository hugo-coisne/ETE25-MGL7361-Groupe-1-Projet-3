package shop.persistence;

import common.DBConnection;
import shop.exception.DTOException;
import shop.exception.DuplicationBookException;
import shop.model.Book;
import shop.dto.BookProperty;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDAO {
    Logger logger = Logger.getLogger(BookDAO.class.getName());

    public void save(Book book) throws Exception {
        // Logic to save the book to a database or any storage
        logger.log(Level.INFO, String.format("Saving book: %s:%s", book.getTitle(), book.getIsbn()));
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO books " +
                                "(title, description, isbn, publication_date, price, stock_quantity)" +
                                " VALUES (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                )) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getDescription());
            statement.setString(3, book.getIsbn());
            statement.setDate(4, book.getPublicationDate());
            statement.setDouble(5, book.getPrice());
            statement.setInt(6, book.getStockQuantity());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                logger.info("Book inserted successfully.");
            } else {
                throw new DTOException("Error inserting book.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Duplicate entry") && errorMessage.contains("isbn")) {
                throw new DuplicationBookException("A book with this ISBN already exists: " + book.getIsbn());
            } else {
                throw new Exception("An error occurred while saving the book: " + errorMessage, e);
            }
        }

    }

    private PreparedStatement buildGetBooksQuery(Connection conn, Map<BookProperty, String> criteria) throws SQLException {
        StringBuilder baseQuery = new StringBuilder("SELECT DISTINCT books.* FROM books ");
        Set<BookProperty> keys = criteria.keySet();

        // Dynamic JOINs based on criteria
        if (keys.contains(BookProperty.PUBLISHER)) {
            baseQuery.append("JOIN publishers ON books.publisher_id = publishers.id ");
        }
        if (keys.contains(BookProperty.AUTHOR)) {
            baseQuery.append("JOIN book_author ON books.id = book_author.book_id ");
            baseQuery.append("JOIN authors ON book_author.author_id = authors.id ");
        }
        if (keys.contains(BookProperty.CATEGORY)) {
            baseQuery.append("JOIN book_category ON books.id = book_category.book_id ");
            baseQuery.append("JOIN categories ON book_category.category_id = categories.id ");
        }

        // Construction de la clause WHERE
        if (!criteria.isEmpty()) {
            baseQuery.append("WHERE ");
            StringJoiner whereClause = new StringJoiner(" AND ");
            for (BookProperty key : keys) {
                whereClause.add(key.getColumn() + " LIKE ?");
            }
            baseQuery.append(whereClause);
        }

        logger.log(Level.INFO, baseQuery.toString());

        PreparedStatement statement = conn.prepareStatement(baseQuery.toString());

        int index = 1;
        for (BookProperty key : keys) {
            statement.setString(index++, "%" + criteria.get(key) + "%");
        }

        return statement;
    }

    public List<Book> getBooksBy(Map<BookProperty, String> criteria) throws Exception {
        List<Book> books = new ArrayList<>();
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement statement = buildGetBooksQuery(conn, criteria);
                ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()) {
                Book book = new Book(
                        rs.getString("title"),
                        rs.getString("isbn"),
                        rs.getDouble("price")
                );
                book.setDescription(rs.getString("description"));
                book.setPublicationDate(rs.getDate("publication_date"));
                book.setStockQuantity(rs.getInt("stock_quantity"));

                books.add(book);
            }
        } catch (SQLException e) {
            throw new Exception("Error retrieving books", e);
        }
        logger.log(Level.INFO, String.format("Retrieving books: %s", books));
        return books;
    }

    public void deleteBook(Book book) throws Exception {
        logger.log(Level.INFO, "Deleting book: " + book.getTitle() + " (ISBN: " + book.getIsbn() + ")");

        try (
                Connection conn = DBConnection.getConnection()
        ) {
            conn.setAutoCommit(false); // début de transaction

            //Récupérer l’ID du livre via l’ISBN
            int bookId = -1;
            try (PreparedStatement getIdStmt = conn.prepareStatement(
                    "SELECT id FROM books WHERE isbn = ?")) {
                getIdStmt.setString(1, book.getIsbn());
                try (ResultSet rs = getIdStmt.executeQuery()) {
                    if (rs.next()) {
                        bookId = rs.getInt("id");
                    } else {
                        throw new DTOException("No book found with ISBN: " + book.getIsbn());
                    }
                }
            }

            //Supprimer les associations dans book_author
            try (PreparedStatement deleteAuthorsStmt = conn.prepareStatement(
                    "DELETE FROM book_author WHERE book_id = ?")) {
                deleteAuthorsStmt.setInt(1, bookId);
                deleteAuthorsStmt.executeUpdate();
            }

            //Supprimer les associations dans book_category
            try (PreparedStatement deleteCategoriesStmt = conn.prepareStatement(
                    "DELETE FROM book_category WHERE book_id = ?")) {
                deleteCategoriesStmt.setInt(1, bookId);
                deleteCategoriesStmt.executeUpdate();
            }

            //Supprimer le livre lui-même
            try (PreparedStatement deleteBookStmt = conn.prepareStatement(
                    "DELETE FROM books WHERE id = ?")) {
                deleteBookStmt.setInt(1, bookId);
                int rowsDeleted = deleteBookStmt.executeUpdate();
                if (rowsDeleted == 0) {
                    throw new DTOException("Book not deleted. It may not exist.");
                }
            }

            conn.commit();
            logger.info("Book and its associations deleted successfully.");

        } catch (DTOException e) {
            throw e; // on propage cette exception spécifique telle quelle

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting book with ISBN " + book.getIsbn(), e);
            throw new Exception("An error occurred while deleting the book: " + e.getMessage(), e);
        }
    }

    public void setPropertiesFor(Book book, Map<BookProperty, List<String>> properties) throws DTOException {
        Logger logger = Logger.getLogger(BookDAO.class.getName());

        try (Connection conn = DBConnection.getConnection()) {
            for (Map.Entry<BookProperty, List<String>> entry : properties.entrySet()) {
                BookProperty property = entry.getKey();
                List<String> values = entry.getValue();

                if (values == null || values.isEmpty()) continue;

                switch (property) {
                    case PUBLISHER -> {
                        String publisherName = values.get(0);

                        // Vérifie si l'éditeur existe déjà
                        PreparedStatement ps = conn.prepareStatement("SELECT id FROM publishers WHERE name = ?");
                        ps.setString(1, publisherName);
                        ResultSet rs = ps.executeQuery();
                        int publisherId;
                        if (rs.next()) {
                            publisherId = rs.getInt("id");
                        } else {
                            // Créer le publisher s'il n'existe pas
                            PreparedStatement insert = conn.prepareStatement("INSERT INTO publishers(name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                            insert.setString(1, publisherName);
                            insert.executeUpdate();
                            ResultSet genKeys = insert.getGeneratedKeys();
                            genKeys.next();
                            publisherId = genKeys.getInt(1);
                        }

                        PreparedStatement updateBook = conn.prepareStatement("UPDATE books SET publisher_id = ? WHERE isbn = ?");
                        updateBook.setInt(1, publisherId);
                        updateBook.setString(2, book.getIsbn());
                        updateBook.executeUpdate();
                    }

                    case AUTHOR -> {
                        for (String authorName : values) {
                            // Vérifie si l'auteur existe
                            PreparedStatement ps = conn.prepareStatement("SELECT id FROM authors WHERE name = ?");
                            ps.setString(1, authorName);
                            ResultSet rs = ps.executeQuery();
                            int authorId;
                            if (rs.next()) {
                                authorId = rs.getInt("id");
                            } else {
                                PreparedStatement insert = conn.prepareStatement("INSERT INTO authors(name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                                insert.setString(1, authorName);
                                insert.executeUpdate();
                                ResultSet genKeys = insert.getGeneratedKeys();
                                genKeys.next();
                                authorId = genKeys.getInt(1);
                            }

                            // Relier le livre et l’auteur
                            PreparedStatement link = conn.prepareStatement("INSERT IGNORE INTO book_author(book_id, author_id) VALUES ((SELECT id FROM books WHERE isbn = ?), ?)");
                            link.setString(1, book.getIsbn());
                            link.setInt(2, authorId);
                            link.executeUpdate();
                        }
                    }

                    case CATEGORY -> {
                        for (String categoryName : values) {
                            // Vérifie si la catégorie existe
                            PreparedStatement ps = conn.prepareStatement("SELECT id FROM categories WHERE name = ?");
                            ps.setString(1, categoryName);
                            ResultSet rs = ps.executeQuery();
                            int categoryId;
                            if (rs.next()) {
                                categoryId = rs.getInt("id");
                            } else {
                                PreparedStatement insert = conn.prepareStatement("INSERT INTO categories(name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
                                insert.setString(1, categoryName);
                                insert.executeUpdate();
                                ResultSet genKeys = insert.getGeneratedKeys();
                                genKeys.next();
                                categoryId = genKeys.getInt(1);
                            }

                            // Relier le livre et la catégorie
                            PreparedStatement link = conn.prepareStatement("INSERT IGNORE INTO book_category(book_id, category_id) VALUES ((SELECT id FROM books WHERE isbn = ?), ?)");
                            link.setString(1, book.getIsbn());
                            link.setInt(2, categoryId);
                            link.executeUpdate();
                        }
                    }

                    default -> {
                        // Ignorer les propriétés qui ne nécessitent pas de jointure
                        continue;
                    }
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while setting properties for book " + book.getIsbn(), e);
            throw new DTOException("Unable to set properties for book: " + book.getIsbn());
        }
    }

    public void removePropertiesFrom(Book book, Map<BookProperty, List<String>> properties) throws DTOException {
        Logger logger = Logger.getLogger(BookDAO.class.getName());

        try (Connection conn = DBConnection.getConnection()) {
            for (Map.Entry<BookProperty, List<String>> entry : properties.entrySet()) {
                BookProperty property = entry.getKey();
                List<String> values = entry.getValue();

                if (values == null || values.isEmpty()) continue;

                switch (property) {
                    case PUBLISHER -> {
                        // Si l'éditeur à retirer correspond bien à celui du livre, on le retire (on met publisher_id à null)
                        String publisherName = values.get(0);
                        PreparedStatement ps = conn.prepareStatement("SELECT id FROM publishers WHERE name = ?");
                        ps.setString(1, publisherName);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            int publisherId = rs.getInt("id");
                            PreparedStatement update = conn.prepareStatement(
                                    "UPDATE books SET publisher_id = NULL WHERE isbn = ? AND publisher_id = ?"
                            );
                            update.setString(1, book.getIsbn());
                            update.setInt(2, publisherId);
                            update.executeUpdate();
                        }
                    }

                    case AUTHOR -> {
                        for (String authorName : values) {
                            PreparedStatement ps = conn.prepareStatement("SELECT id FROM authors WHERE name = ?");
                            ps.setString(1, authorName);
                            ResultSet rs = ps.executeQuery();
                            if (rs.next()) {
                                int authorId = rs.getInt("id");
                                PreparedStatement delete = conn.prepareStatement(
                                        "DELETE FROM book_author WHERE book_id = (SELECT id FROM books WHERE isbn = ?) AND author_id = ?"
                                );
                                delete.setString(1, book.getIsbn());
                                delete.setInt(2, authorId);
                                delete.executeUpdate();
                            }
                        }
                    }

                    case CATEGORY -> {
                        for (String categoryName : values) {
                            PreparedStatement ps = conn.prepareStatement("SELECT id FROM categories WHERE name = ?");
                            ps.setString(1, categoryName);
                            ResultSet rs = ps.executeQuery();
                            if (rs.next()) {
                                int categoryId = rs.getInt("id");
                                PreparedStatement delete = conn.prepareStatement(
                                        "DELETE FROM book_category WHERE book_id = (SELECT id FROM books WHERE isbn = ?) AND category_id = ?"
                                );
                                delete.setString(1, book.getIsbn());
                                delete.setInt(2, categoryId);
                                delete.executeUpdate();
                            }
                        }
                    }

                    default -> {
                        // On ignore les propriétés simples (title, isbn, etc.)
                    }
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while removing properties from book " + book.getIsbn(), e);
            throw new DTOException("Unable to remove properties from book: " + book.getIsbn());
        }
    }

    public boolean isInStock(Book book) throws SQLException {
        String query = "SELECT stock_quantity FROM books WHERE isbn = ?";
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, book.getIsbn());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("stock_quantity") > 0;
            }
            return false;
        }
    }

    public boolean isSufficientlyInStock(Book book, int quantity) throws SQLException {
        String query = "SELECT stock_quantity FROM books WHERE isbn = ?";
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setString(1, book.getIsbn());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("stock_quantity") >= quantity;
            }
            return false;
        }
    }

}
