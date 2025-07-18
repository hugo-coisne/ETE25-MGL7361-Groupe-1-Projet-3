package ca.uqam.mgl7361.lel.gp1.delivery.persistence;

import ca.uqam.mgl7361.lel.gp1.common.DBConnection;
import ca.uqam.mgl7361.lel.gp1.delivery.model.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO {

    public AddressDAO() {
    }

    public void create(Address address) throws Exception {
        String sql = "INSERT INTO addresses (account_id, first_name, last_name, phone, street, city, postal_code) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, address.getAccountId());
            stmt.setString(2, address.getFirstName());
            stmt.setString(3, address.getLastName());
            stmt.setString(4, address.getPhone());
            stmt.setString(5, address.getStreet());
            stmt.setString(6, address.getCity());
            stmt.setString(7, address.getPostalCode());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                address.setId(keys.getInt(1));
            }
        }
    }

    public Address findById(int id) throws Exception {
        String sql = "SELECT * FROM addresses WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return map(rs);
            }
        }
        return null;
    }

    public List<Address> findByAccountId(int accountId) throws Exception {
        String sql = "SELECT * FROM addresses WHERE account_id = ?";
        List<Address> addresses = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                addresses.add(map(rs));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Exception("Error finding addresses by account ID: " + e.getMessage(), e);
        }
        return addresses;
    }

    public void update(Address address) throws Exception {
        String sql = "UPDATE addresses SET first_name = ?, last_name = ?, phone = ?, street = ?, city = ?, postal_code = ? "
                +
                "WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, address.getFirstName());
            stmt.setString(2, address.getLastName());
            stmt.setString(3, address.getPhone());
            stmt.setString(4, address.getStreet());
            stmt.setString(5, address.getCity());
            stmt.setString(6, address.getPostalCode());
            stmt.setInt(7, address.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Exception("Error updating address: " + e.getMessage(), e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM addresses WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
