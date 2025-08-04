package ca.uqam.mgl7361.lel.gp1.delivery.persistence;

import ca.uqam.mgl7361.lel.gp1.delivery.DBConnection;
import ca.uqam.mgl7361.lel.gp1.delivery.model.Delivery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeliveryDAO {

    Logger logger = LogManager.getLogger(DeliveryDAO.class);

    public DeliveryDAO() {
    }

    public Delivery createDelivery(Delivery delivery) throws Exception {
        int addressId = delivery.getAddressId(); // ici, on suppose que AddressDTO a un id

        String sql = "INSERT INTO deliveries (order_id, address_id, delivery_date, status) VALUES (?, ?, ?, ?)";

        logger.info("order ID " + delivery.getOrderId());

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, delivery.getOrderId());
            stmt.setInt(2, addressId);
            stmt.setDate(3, new Date(delivery.getDate().getTime()));
            stmt.setString(4, delivery.getStatus());
            logger.debug(stmt.toString());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Creating delivery failed, no rows affected.");
                throw new SQLException("Creating delivery failed, no rows affected.");
            }
            return delivery;
        } catch (SQLException e) {
            logger.error(e);
            throw new Exception("Error creating delivery: " + e.getMessage(), e);
        }
    }

    private Delivery mapResultSetToDeliveryAddress(ResultSet rs) throws SQLException {
        Delivery delivery = new Delivery(
                rs.getInt("id"),
                rs.getInt("address_id"),
                rs.getDate("delivery_date"),
                rs.getString("status"),
                rs.getInt("order_id"));
        return delivery;
    }

    public void update(Delivery delivery) throws Exception {
        int orderId = delivery.getOrderId();

        String sql = "UPDATE deliveries SET address_id = ?, delivery_date = ?, status = ? WHERE order_id = ?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, delivery.getAddressId());
            stmt.setDate(2, new Date(delivery.getDate().getTime()));
            stmt.setString(3, delivery.getStatus());
            stmt.setInt(4, orderId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Updating delivery failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new Exception("Error updating delivery: " + e.getMessage(), e);
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM deliveries WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error deleting delivery: " + e.getMessage(), e);
        }
    }

    // R�cup�re toutes les livraisons avec un statut sp�cifique
    public List<Delivery> findByStatus(String status) throws Exception {
        String sql = """
                    SELECT d.*, a.first_name, a.last_name, a.street, a.city, a.postal_code, a.phone
                    FROM deliveries d
                    JOIN addresses a ON d.address_id = a.id
                    WHERE d.status = ?
                """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            List<Delivery> deliveries = new ArrayList<>();
            while (rs.next()) {
                deliveries.add(mapResultSetToDeliveryAddress(rs));
            }
            return deliveries;
        } catch (SQLException e) {
            throw new Exception("Error retrieving deliveries by status: " + e.getMessage(), e);
        }
    }

    private Delivery mapResultSetToDelivery(ResultSet rs) {
        try {
            Delivery delivery = new Delivery();
            delivery.setId(rs.getInt("id"));
            delivery.setDate(rs.getDate("delivery_date"));
            delivery.setStatus(rs.getString("status"));
            delivery.setOrderId(rs.getInt("order_id"));
            return delivery;
        } catch (SQLException e) {
            logger.error("Error mapping ResultSet to Delivery: " + e.getMessage(), e);
            return null;
        }
    }

    // R�cup�re toutes les livraisons dont le statut est diff�rent de celui donn�
    public List<Delivery> findByStatusNot(String status) throws Exception {
        String sql = "SELECT * FROM deliveriesWHERE status != ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            List<Delivery> deliveries = new ArrayList<>();
            while (rs.next()) {
                deliveries.add(mapResultSetToDelivery(rs));
            }
            return deliveries;
        } catch (SQLException e) {
            throw new Exception("Error retrieving deliveries by status not equal to: " + e.getMessage(), e);
        }
    }

    public List<Delivery> findByOrderId(int orderId) throws Exception {
        String sql = "SELECT * FROM deliveries WHERE order_id=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            List<Delivery> deliveries = new ArrayList<>();
            while (rs.next()) {
                deliveries.add(mapResultSetToDeliveryAddress(rs));
            }
            return deliveries;
        } catch (SQLException e) {
            throw new Exception("Error retrieving deliveries by account ID: " + e.getMessage(), e);
        }
    }

    // public Delivery findByOrderNumber(String orderNumber) throws Exception {
    // String sql = """
    // SELECT d.id, d.delivery_date, d.status,
    // d.address_id, d.order_id
    // FROM deliveries d
    // JOIN orders o ON d.order_id = o.id
    // WHERE o.order_number = ?
    // """;

    // Connection conn = DBConnection.getConnection();
    // try (PreparedStatement stmt = conn.prepareStatement(sql)) {
    // stmt.setString(1, orderNumber);
    // ResultSet rs = stmt.executeQuery();
    // if (rs.next()) {
    // int deliveryId = rs.getInt("id");
    // Date date = rs.getDate("delivery_date");
    // String status = rs.getString("status");

    // int addressId = rs.getInt("address_id");
    // int orderId = rs.getInt("order_id");

    // AddressDTO addressDTO = toDTO(addressDAO.findById(addressId));
    // OrderDTO orderDTO = orderDAO.findById(orderId);

    // return new Delivery(deliveryId, addressDTO, date, status, orderDTO);
    // } else {
    // return null;
    // }
    // } catch (SQLException e) {
    // throw new Exception("Error finding delivery by order number: " +
    // e.getMessage(), e);
    // }
    // }

}
