package ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.persistence;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.DBConnection;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.model.Address;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {
    private final Connection connection = DBConnection.getConnection();

    public AddressDAO() throws SQLException {}

    public void create(Address address) throws SQLException {
        String sql = "INSERT INTO addresses (account_id, first_name, last_name, phone, street, city, postal_code) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
                    address.setId(keys.getInt(1));
                }
            }
        }
    }

    public Address findById(int id) throws SQLException {
        String sql = "SELECT * FROM addresses WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Address> findByAccountId(int accountId) throws SQLException {
        String sql = "SELECT * FROM addresses WHERE account_id = ?";
        List<Address> addresses = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    addresses.add(map(rs));
                }
            }
        }
        return addresses;
    }

    public void update(Address address) throws SQLException {
        String sql = "UPDATE addresses SET first_name = ?, last_name = ?, phone = ?, street = ?, city = ?, postal_code = ? " +
                "WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, address.getFirstName());
            stmt.setString(2, address.getLastName());
            stmt.setString(3, address.getPhone());
            stmt.setString(4, address.getStreet());
            stmt.setString(5, address.getCity());
            stmt.setString(6, address.getPostalCode());
            stmt.setInt(7, address.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM addresses WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
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

