package ca.uqam.mgl7361.lel.gp1.account.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.logging.Logger;

import ca.uqam.mgl7361.lel.gp1.account.exception.DuplicateEmailException;
import ca.uqam.mgl7361.lel.gp1.account.exception.InvalidCredentialsException;
import ca.uqam.mgl7361.lel.gp1.account.model.Account;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.DBConnection;

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
        logger.info("Creating account in database for: " + account.toString());

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO accounts (first_name, last_name, phone, email, password) VALUES (?, ?, ?, ?, ?)",
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
            throw new RuntimeException("Erreur lors de l'insertion dans la base de données", e);
        }
    }

    public Account findByEmailAndPassword(String email, String password) throws InvalidCredentialsException {
        logger.info("Finding account by email: " + email);

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "SELECT * FROM accounts WHERE email = ? AND password = ?")) {

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phone = resultSet.getString("phone");

                Account account = new Account(firstName, lastName, phone, email, password);
                account.setId(id);

                logger.info("Account found: " + account.toString());
                return account;
            }
            throw new InvalidCredentialsException(email);

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du compte dans la base de données", e);
        }
    }

    public void deleteAccountWithId(int id) {
        logger.info("Deleting account with ID: " + id);

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement("DELETE FROM accounts WHERE id = ?")) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Account with ID " + id + " deleted successfully.");
            } else {
                logger.warning("No account found with ID " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du compte dans la base de données", e);
        }
    }

    public void update(Account authenticatedAccount) {
        logger.info("Updating account in database for: " + authenticatedAccount.toString());

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "UPDATE accounts SET first_name = ?, last_name = ?, phone = ?, email = ?, password = ? WHERE id = ?")) {

            statement.setString(1, authenticatedAccount.getFirstName());
            statement.setString(2, authenticatedAccount.getLastName());
            statement.setString(3, authenticatedAccount.getPhone());
            statement.setString(4, authenticatedAccount.getEmail());
            statement.setString(5, authenticatedAccount.getPassword());
            statement.setInt(6, authenticatedAccount.getId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                logger.info("Account updated successfully.");
            } else {
                logger.warning("No account found with ID " + authenticatedAccount.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du compte dans la base de données", e);
        }
    }
}
