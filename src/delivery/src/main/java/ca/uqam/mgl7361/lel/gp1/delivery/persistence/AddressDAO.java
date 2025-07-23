package ca.uqam.mgl7361.lel.gp1.delivery.persistence;

import ca.uqam.mgl7361.lel.gp1.delivery.model.Address;
import ca.uqam.mgl7361.lel.gp1.common.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {

    private static final Logger logger = LogManager.getLogger(AddressDAO.class);

    private static final String INSERT_SQL = """
            INSERT INTO addresses (account_id, first_name, last_name, phone, street, city, postal_code)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM addresses WHERE id = ?";
    private static final String SELECT_BY_ACCOUNT_SQL = "SELECT * FROM addresses WHERE account_id = ?";
    private static final String UPDATE_SQL = """
            UPDATE addresses
            SET first_name = ?, last_name = ?, phone = ?, street = ?, city = ?, postal_code = ?
            WHERE id = ?
            """;
    private static final String DELETE_SQL = "DELETE FROM addresses WHERE id = ?";

    public void create(Address address) throws Exception {
        logger.debug("Attempting to insert address: {}", address);
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, address.getAccountId());
            stmt.setString(2, address.getFirstName());
            stmt.setString(3, address.getLastName());
            stmt.setString(4, address.getPhone());
            stmt.setString(5, address.getStreet());
            stmt.setString(6, address.getCity());
            stmt.setString(7, address.getPostalCode());
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    int generatedId = keys.getInt(1);
                    address.setId(generatedId);
                    logger.info("Address inserted successfully with ID {}", generatedId);
                }
            }
        } catch (SQLException e) {
            logger.error("Error inserting address: {}", address, e);
            throw new Exception("Failed to insert address", e);
        }
    }

    public Address findById(int id) throws Exception {
        logger.debug("Looking for address with ID {}", id);
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Address address = map(rs);
                    logger.info("Address found: {}", address);
                    return address;
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding address by ID: {}", id, e);
            throw new Exception("Failed to find address by ID", e);
        }
        logger.info("No address found with ID {}", id);
        return null;
    }

    public List<Address> findByAccountId(int accountId) throws Exception {
        logger.debug("Retrieving addresses for account ID {}", accountId);
        List<Address> addresses = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ACCOUNT_SQL)) {

            stmt.setInt(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    addresses.add(map(rs));
                }
            }
            logger.info("Found {} address(es) for account ID {}", addresses.size(), accountId);
        } catch (SQLException e) {
            logger.error("Error finding addresses by account ID: {}", accountId, e);
            throw new Exception("Failed to find addresses by account ID", e);
        }
        return addresses;
    }

    public void update(Address address) throws Exception {
        logger.debug("Updating address: {}", address);
        try (Connection connection = ca.uqam.mgl7361.lel.gp1.common.DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(UPDATE_SQL)) {

            stmt.setString(1, address.getFirstName());
            stmt.setString(2, address.getLastName());
            stmt.setString(3, address.getPhone());
            stmt.setString(4, address.getStreet());
            stmt.setString(5, address.getCity());
            stmt.setString(6, address.getPostalCode());
            stmt.setInt(7, address.getId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                logger.info("Address with ID {} updated successfully", address.getId());
            } else {
                logger.warn("No address updated for ID {}", address.getId());
            }
        } catch (SQLException e) {
            logger.error("Error updating address: {}", address, e);
            throw new Exception("Failed to update address", e);
        }
    }

    public boolean delete(int id) throws Exception {
        logger.debug("Attempting to delete address with ID {}", id);
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(DELETE_SQL)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                logger.info("Address with ID {} deleted successfully", id);
                return true;
            } else {
                logger.warn("No address found to delete with ID {}", id);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error deleting address with ID {}", id, e);
            throw new Exception("Failed to delete address", e);
        }
    }

    private Address map(ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setId(rs.getInt("id"));
        address.setAccountId(rs.getInt("account_id"));
        address.setFirstName(rs.getString("first_name"));
        address.setLastName(rs.getString("last_name"));
        address.setPhone(rs.getString("phone"));
        address.setStreet(rs.getString("street"));
        address.setCity(rs.getString("city"));
        address.setPostalCode(rs.getString("postal_code"));
        return address;
    }
}
