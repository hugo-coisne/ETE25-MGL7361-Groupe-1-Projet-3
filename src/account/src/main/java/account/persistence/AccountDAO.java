package account.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.logging.Logger;

import account.business.entities.Account;
import account.business.exception.DuplicateEmailException;
import common.DBConnection;

public class AccountDAO {

    Logger logger = Logger.getLogger(AccountDAO.class.getName());

    static AccountDAO instance;

    public static AccountDAO getInstance() {
        if (instance == null) {
            instance = new AccountDAO();
        }
        return instance;
    }

    public void insert(Account account) throws DuplicateEmailException {
        // Implementation to create an account in the database
        // This is a placeholder for actual database interaction code
        logger.info("Creating account in database for: " + account.toString());

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO Account (first_name, last_name, phone, email, password) VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, account.getFirstName());
            statement.setString(2, account.getLastName());
            statement.setString(3, account.getPhone());
            statement.setString(4, account.getEmail());
            statement.setString(5, account.getPassword());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        account.setId(generatedId);
                        logger.info("Account created successfully with ID " + account.getId());
                    } else {
                        logger.info("No generated ID found.");
                    }
                }
            } else {
                logger.info("Database insertion failed, no rows affected.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            logger.severe("SQL Integrity Constraint Violation: " + e.getMessage());

            String errorMessage = e.getMessage();
            // Check if the error is due to a duplicate email
            if (errorMessage.contains("Duplicate entry") && errorMessage.contains("email")) {
                throw new DuplicateEmailException(account.getEmail());
            } else {
                throw new RuntimeException("Erreur d'intégrité SQL inconnue : " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'insertion en base", e);
        }
    }

}
