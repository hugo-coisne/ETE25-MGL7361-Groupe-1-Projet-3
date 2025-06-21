package shop.persistence;

import common.DBConnection;
import shop.exception.DTOException;
import shop.exception.DuplicationBookException;
import shop.model.Book;

import java.sql.*;
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
                        "INSERT INTO Book (title, isbn, price) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                )) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getIsbn());
            statement.setDouble(3, book.getPrice());
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
}
