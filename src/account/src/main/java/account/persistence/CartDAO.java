package account.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Logger;

import account.dto.AccountDTO;
import account.model.Account;
import account.model.Cart;
import common.DBConnection;
import shop.dto.BookDTO;
import shop.dto.PublisherDTO;

public class CartDAO {

    Logger logger = Logger.getLogger(CartDAO.class.getName());

    public Cart createCartFor(AccountDTO accountDTO) {
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO carts (account_id, total_price) VALUES (?, 0.0)", Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, accountDTO.getId());
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                logger.info("Cart created successfully for account ID: " + accountDTO.getId());
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    double totalPrice = 0.0; // Initial total price
                    Cart cart = new Cart(generatedId, totalPrice);
                    logger.info("Cart ID: " + generatedId + " created for account ID: " + accountDTO.getId());
                    return cart; // Return the created cart
                } else {
                    throw new SQLException("No generated ID found for cart creation.");
                }
            } else {
                throw new SQLException("No rows inserted, cart creation failed for account ID: " + accountDTO.getId());
            }
        } catch (SQLException e) {
            logger.severe("Error inserting cart for account: " + e.getMessage());
            throw new RuntimeException("Error inserting cart for account", e);
        }
    }

    public Cart getCart(AccountDTO accountDto) {
        logger.info("Retrieving cart for account ID: " + accountDto.getId());
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
                    logger.warning("Book data is incomplete or invalid, skipping this book.");
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
            logger.info("Returning " + cart.toString());
            return cart;

        } catch (SQLException e) {
            logger.severe("Error retrieving cart: " + e.getMessage());
            throw new RuntimeException("Error retrieving cart", e);
        }
    }

    public void addBookToCart(AccountDTO accountDto, BookDTO bookDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addBookToCart'"); 
    }

    public void removeBookFromCart(Account account, BookDTO bookDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeBookFromCart'");
    }

    public void clearCart(Account account) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clearCart'");
    }

    public double getCartTotalPrice(AccountDTO accountDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCartTotalPrice'");
    }

}
