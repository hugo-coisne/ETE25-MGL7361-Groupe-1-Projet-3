package shop.persistence;

import common.DBConnection;
import shop.exception.DTOException;
import shop.exception.DuplicationBookException;
import shop.model.Book;
import shop.model.BookProperty;

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
                        "INSERT INTO Book " +
                                "(title, description, isbn, publication_date, price, stock_quantity)" +
                                " VALUES (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                )) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getDescription());
            statement.setString(3, book.getIsbn());
            statement.setTimestamp(4, book.getPublicationDate());
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

        // Gestion des JOINs dynamiques
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
                book.setPublicationDate(rs.getTimestamp("publication_date"));
                book.setStockQuantity(rs.getInt("stock_quantity"));

                books.add(book);
            }
        } catch (SQLException e) {
            throw new Exception("Error retrieving books", e);
        }
        logger.log(Level.INFO, String.format("Retrieving books: %s", books));
        return books;
    }
}
