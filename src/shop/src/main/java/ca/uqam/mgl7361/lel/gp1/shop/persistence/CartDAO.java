package ca.uqam.mgl7361.lel.gp1.shop.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.shop.exception.InvalidCartException;
import ca.uqam.mgl7361.lel.gp1.shop.model.Cart;
import ca.uqam.mgl7361.lel.gp1.common.DBConnection;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.PublisherDTO;

public class CartDAO {

    Logger logger = LogManager.getLogger(CartDAO.class.getName());

    public Cart createCartFor(AccountDTO accountDTO) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO carts (account_id, total_price) VALUES (?, 0.0)",
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, accountDTO.getId());
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                logger.debug("Cart created successfully for account ID: " + accountDTO.getId());
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    double totalPrice = 0.0; // Initial total price
                    Cart cart = new Cart(generatedId, totalPrice);
                    logger.debug("Cart ID: " + generatedId + " created for account ID: " + accountDTO.getId());
                    return cart; // Return the created cart
                } else {
                    throw new SQLException("No generated ID found for cart creation.");
                }
            } else {
                throw new SQLException("No rows inserted, cart creation failed for account ID: " + accountDTO.getId());
            }
        } catch (SQLException e) {
            logger.error("Error inserting cart for account: " + e.getMessage());
            throw new RuntimeException("Error inserting cart for account", e);
        }
    }

    public Cart getCart(AccountDTO accountDto) {
        logger.debug("Retrieving cart for account ID: " + accountDto.getId());
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT \n" + //
                                "carts.id as cart_id,  \n" +
                                "cart_book.book_id,\n" +
                                "books.title as book_title,\n" +
                                "books.isbn as book_isbn,\n" +
                                "books.description as book_description,\n" +
                                "books.publication_date as book_publication_date,\n" +
                                "books.price as book_unit_price,\n" +
                                "books.stock_quantity as book_stock_quantity,\n" +
                                "cart_book.quantity as cart_book_quantity, \n" +
                                "publishers.name as publisher_name, \n" +
                                "publishers.id as publisher_id, \n" +
                                "carts.total_price as cart_total_price \n" +
                                "FROM carts \n" +
                                "LEFT JOIN cart_book ON carts.id = cart_book.cart_id \n" +
                                "LEFT JOIN books ON cart_book.book_id = books.id \n" +
                                "LEFT JOIN publishers ON books.publisher_id = publishers.id \n"
                                +
                                "WHERE carts.account_id = ?")) {

            statement.setInt(1, accountDto.getId());

            ResultSet resultSet = statement.executeQuery();
            int cartId = -1;
            double totalPrice = -1.0;
            Map<BookDTO, Integer> booksDto = new java.util.HashMap<>();
            Cart cart = new Cart(cartId, totalPrice);
            while (resultSet.next()) {
                cartId = resultSet.getInt("cart_id");
                totalPrice = resultSet.getDouble("cart_total_price");
                cart.setId(cartId);
                cart.setTotalPrice(totalPrice);
                // int bookId = resultSet.getInt("book_id");
                String bookTitle = resultSet.getString("book_title");
                String bookIsbn = resultSet.getString("book_isbn");
                String bookDescription = resultSet.getString("book_description");
                Date bookPublicationDate = resultSet.getDate("book_publication_date");
                double bookUnitPrice = resultSet.getDouble("book_unit_price");
                int bookStockQuantity = resultSet.getInt("book_stock_quantity");
                int cartBookQuantity = resultSet.getInt("cart_book_quantity");
                // int publisherId = resultSet.getInt("publisher_id");
                String publisherName = resultSet.getString("publisher_name");
                if (bookTitle == null || bookIsbn == null || bookUnitPrice <= 0) {
                    logger.error("Book data is incomplete or invalid bookTitle=" + bookTitle + ", bookIsbn=" + bookIsbn
                            + ", bookUnitPrice=" + bookUnitPrice + ", skipping this book.");
                    continue; // Skip this book if data is incomplete
                }
                BookDTO bookDto = new BookDTO(bookTitle, bookIsbn, bookUnitPrice);
                // bookDto.setId(bookId);
                bookDto.setDescription(bookDescription);
                bookDto.setPublicationDate(bookPublicationDate);
                bookDto.setStockQuantity(bookStockQuantity);
                bookDto.setPublisher(new PublisherDTO(publisherName));
                booksDto.put(bookDto, cartBookQuantity);
            }
            cart.setBooksDto(booksDto);
            logger.debug("Returning " + cart.toString());
            return cart;

        } catch (SQLException e) {
            logger.error("Error retrieving cart: " + e.getMessage());
            throw new RuntimeException("Error retrieving cart", e);
        }
    }

    public void addBookToCart(AccountDTO accountDto, BookDTO bookDto) throws InvalidCartException {
        try (Connection conn = DBConnection.getConnection();) {

            // Retrieve the cart ID for the account
            logger.debug("Getting cart ID for account ID: " + accountDto.getId());
            PreparedStatement cartStatement = conn.prepareStatement(
                    "SELECT id FROM carts WHERE account_id = ?");
            cartStatement.setInt(1, accountDto.getId());
            logger.debug("Executing query " + cartStatement);
            ResultSet rs = cartStatement.executeQuery();

            if (!rs.next()) {
                logger.error("No cart found for account ID: " + accountDto.getId());
                throw new InvalidCartException("No cart found for account ID: " + accountDto.getId());
            }
            int cartId = rs.getInt("id");
            logger.debug("Cart ID retrieved: " + cartId);

            // Retrieve the book ID
            logger.debug("Getting book ID for book title: " + bookDto.getTitle());
            PreparedStatement bookStatement = conn.prepareStatement(
                    "SELECT id FROM books WHERE isbn LIKE ?"); // using the id would be better, but ids aren't provided
                                                               // in
                                                               // dtos
            bookStatement.setString(1, bookDto.getIsbn());

            logger.debug("Executing query " + bookStatement);
            ResultSet brs = bookStatement.executeQuery();

            if (!brs.next()) {
                logger.error("No book found for title : " + bookDto.getTitle());
                throw new SQLException("No book found for title : " + bookDto.getTitle());
            }
            int bookId = brs.getInt("id");
            logger.debug("Book ID retrieved: " + bookId);
            // Check if the book is already in the cart
            PreparedStatement checkStatement = conn.prepareStatement(
                    "SELECT quantity FROM cart_book WHERE cart_id = ? AND book_id = ?");
            checkStatement.setInt(1, cartId);
            checkStatement.setInt(2, bookId);
            logger.debug("Executing query " + checkStatement);
            ResultSet checkRs = checkStatement.executeQuery();

            if (checkRs.next()) {
                int quantity = checkRs.getInt("quantity");
                // System.out.println("Quantity in cart: " + quantity + "\n\n\n");
                if (quantity > 0) {
                    logger.debug("Book already in cart, incrementing quantity for account ID: " + accountDto.getId());
                    // Increment the quantity of the book in the cart
                    PreparedStatement updateStatement = conn.prepareStatement(
                            "UPDATE cart_book SET quantity = quantity + 1 WHERE cart_id = ? AND book_id = ?");
                    updateStatement.setInt(1, cartId);
                    updateStatement.setInt(2, bookId);
                    updateStatement.executeUpdate();
                    logger.debug("Book quantity updated successfully for account ID: " + accountDto.getId());
                }
            } else {
                logger.debug(
                        "Adding book to cart: " + bookDto.getTitle() + " for account ID: " + accountDto.getId());

                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO cart_book (cart_id, book_id, quantity) VALUES (?, ?, 1)");

                // Insert the book into the cart
                statement.setInt(1, cartId);
                statement.setInt(2, bookId);
                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    logger.debug("Book added to cart successfully for account ID: " + accountDto.getId());
                } else {
                    logger.error("Failed to add book to cart for account ID: " + accountDto.getId());
                }
            }

            // update the total price of the cart
            PreparedStatement updateStatement = conn.prepareStatement(
                    "UPDATE carts SET total_price = total_price + ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
            updateStatement.setDouble(1, bookDto.getPrice());
            updateStatement.setInt(2, cartId);
            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                logger.debug("Cart total price updated successfully for account ID: " + accountDto.getId());
            } else {
                logger.error("Failed to update cart total price for account ID: " + accountDto.getId());
            }
        } catch (SQLException e) {
            logger.error("Error adding book to cart: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void removeBookFromCart(int accountId, BookDTO bookDto) throws InvalidCartException {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT id FROM carts WHERE account_id = ?")) {

            statement.setInt(1, accountId);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                logger.error("No cart found for account ID: " + accountId);
                throw new InvalidCartException("No cart found for account ID: " + accountId);
            }
            int cartId = rs.getInt("id");

            // Retrieve the book ID
            PreparedStatement bookStatement = conn.prepareStatement(
                    "SELECT id FROM books WHERE title LIKE ?");
            bookStatement.setString(1, bookDto.getTitle());
            ResultSet brs = bookStatement.executeQuery();

            if (!brs.next()) {
                logger.error("No book found for title : " + bookDto.getTitle());
                throw new SQLException("No book found for title : " + bookDto.getTitle());
            }
            int bookId = brs.getInt("id");

            // Remove the book from the cart
            PreparedStatement substractStatement = conn.prepareStatement(
                    "UPDATE cart_book SET quantity = quantity - 1 WHERE cart_id = ? AND book_id = ?");
            substractStatement.setInt(1, cartId);
            substractStatement.setInt(2, bookId);
            int rowsDeleted = substractStatement.executeUpdate();

            if (rowsDeleted > 0) {
                logger.debug("Book quantity decremented successfully for account ID: " + accountId);
            } else {
                logger.error("No book found in cart to decrement for account ID: " + accountId);
                throw new SQLException("No book found in cart to decrement for account ID: " + accountId);
            }
            // Check if the book quantity is now zero, if so, remove it from the cart
            PreparedStatement checkStatement = conn.prepareStatement(
                    "DELETE FROM cart_book WHERE cart_id = ? AND book_id = ? AND quantity < 1");
            checkStatement.setInt(1, cartId);
            checkStatement.setInt(2, bookId);
            int rowsRemoved = checkStatement.executeUpdate();
            if (rowsRemoved > 0) {
                logger.debug("Book removed from cart successfully for account ID: " + accountId);
            } else {
                logger.error("No book found in cart to remove for account ID: " + accountId);
            }

            // Update the total price of the cart
            PreparedStatement updateStatement = conn.prepareStatement(
                    "UPDATE carts SET total_price = total_price - ? WHERE id = ?");
            updateStatement.setDouble(1, bookDto.getPrice());
            updateStatement.setInt(2, cartId);
            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                logger.debug("Cart total price updated successfully for account ID: " + accountId);
            } else {
                logger.error("Failed to update cart total price for account ID: " + accountId);
            }
        } catch (SQLException e) {
            logger.error("Error removing book from cart: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void clearCart(AccountDTO account) throws InvalidCartException {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT id FROM carts WHERE account_id = ?");

            statement.setInt(1, account.getId());
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                logger.error("No cart found for account ID: " + account.getId());
                throw new InvalidCartException("No cart found for account ID: " + account.getId());
            }
            int cartId = rs.getInt("id");
            PreparedStatement deleteStatement = conn.prepareStatement(
                    "DELETE FROM cart_book WHERE cart_id = ?");
            deleteStatement.setInt(1, cartId);
            deleteStatement.executeUpdate();
            logger.debug("Cart cleared for account ID: " + account.getId());
            PreparedStatement deleteCartStatement = conn.prepareStatement(
                    "DELETE FROM carts WHERE id = ?");
            deleteCartStatement.setInt(1, cartId);
            deleteCartStatement.executeUpdate();
            logger.debug("Cart deleted for account ID: " + account.getId());
        } catch (SQLException e) {
            logger.error("Error clearing cart: " + e.getMessage());
            throw new RuntimeException("Error clearing cart", e);
        }
    }

    public Cart getCartFor(int accountId) {
        logger.debug("Retrieving cart for account ID: " + accountId);
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT \n" + //
                                "carts.id as cart_id,  \n" +
                                "cart_book.book_id,\n" +
                                "books.title as book_title,\n" +
                                "books.isbn as book_isbn,\n" +
                                "books.description as book_description,\n" +
                                "books.publication_date as book_publication_date,\n" +
                                "books.price as book_unit_price,\n" +
                                "books.stock_quantity as book_stock_quantity,\n" +
                                "cart_book.quantity as cart_book_quantity, \n" +
                                "publishers.name as publisher_name, \n" +
                                "publishers.id as publisher_id, \n" +
                                "carts.total_price as cart_total_price \n" +
                                "FROM carts \n" +
                                "LEFT JOIN cart_book ON carts.id = cart_book.cart_id \n" +
                                "LEFT JOIN books ON cart_book.book_id = books.id \n" +
                                "LEFT JOIN publishers ON books.publisher_id = publishers.id \n"
                                +
                                "WHERE carts.account_id = ?")) {

            statement.setInt(1, accountId);

            ResultSet resultSet = statement.executeQuery();
            int cartId = -1;
            double totalPrice = -1.0;
            Map<BookDTO, Integer> booksDto = new java.util.HashMap<>();
            Cart cart = new Cart(cartId, totalPrice);
            while (resultSet.next()) {
                cartId = resultSet.getInt("cart_id");
                totalPrice = resultSet.getDouble("cart_total_price");
                cart.setId(cartId);
                cart.setTotalPrice(totalPrice);
                // int bookId = resultSet.getInt("book_id");
                String bookTitle = resultSet.getString("book_title");
                String bookIsbn = resultSet.getString("book_isbn");
                String bookDescription = resultSet.getString("book_description");
                Date bookPublicationDate = resultSet.getDate("book_publication_date");
                double bookUnitPrice = resultSet.getDouble("book_unit_price");
                int bookStockQuantity = resultSet.getInt("book_stock_quantity");
                int cartBookQuantity = resultSet.getInt("cart_book_quantity");
                // int publisherId = resultSet.getInt("publisher_id");
                String publisherName = resultSet.getString("publisher_name");
                if (bookTitle == null || bookIsbn == null || bookUnitPrice <= 0) {
                    logger.error("Book data is incomplete or invalid bookTitle=" + bookTitle + ", bookIsbn=" + bookIsbn
                            + ", bookUnitPrice=" + bookUnitPrice + ", skipping this book.");
                    continue; // Skip this book if data is incomplete
                }
                BookDTO bookDto = new BookDTO(bookTitle, bookIsbn, bookUnitPrice);
                // bookDto.setId(bookId);
                bookDto.setDescription(bookDescription);
                bookDto.setPublicationDate(bookPublicationDate);
                bookDto.setStockQuantity(bookStockQuantity);
                bookDto.setPublisher(new PublisherDTO(publisherName));
                booksDto.put(bookDto, cartBookQuantity);
            }
            cart.setBooksDto(booksDto);
            logger.debug("Returning " + cart.toString());
            return cart;

        } catch (SQLException e) {
            logger.error("Error retrieving cart: " + e.getMessage());
            throw new RuntimeException("Error retrieving cart", e);
        }
    }

    public void clearCart(int id) {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement deleteStatement = conn.prepareStatement(
                    "DELETE FROM cart_book WHERE cart_id = ?");
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
            logger.debug("Cart cleared for cart ID: " + id);
            PreparedStatement deleteCartStatement = conn.prepareStatement(
                    "DELETE FROM carts WHERE id = ?");
            deleteCartStatement.setInt(1, id);
            deleteCartStatement.executeUpdate();
            logger.debug("Cart deleted for cart ID: " + id);
        } catch (SQLException e) {
            logger.error("Error clearing cart: " + e.getMessage());
            throw new RuntimeException("Error clearing cart", e);
        }
    }

}
