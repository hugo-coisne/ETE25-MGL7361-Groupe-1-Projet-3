package ca.uqam.mgl7361.lel.gp1.delivery.persistence;

import ca.uqam.mgl7361.lel.gp1.common.DBConnection;
import ca.uqam.mgl7361.lel.gp1.delivery.dto.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.dto.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.model.Delivery;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;

import static ca.uqam.mgl7361.lel.gp1.delivery.business.mapper.AddressMapper.toDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO {

    public DeliveryDAO() {}

    public DeliveryDTO createDelivery(DeliveryDTO delivery, OrderDTO order) throws Exception {
        int addressId = delivery.getAddress().getId(); // ici, on suppose que AddressDTO a un id

        String sql = "INSERT INTO deliveries (order_id, address_id, delivery_date, status) VALUES (?, ?, ?, ?)";
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getId());
            stmt.setInt(2, addressId);
            stmt.setDate(3, delivery.getDeliveryDate());
            stmt.setString(4, delivery.getDeliveryStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating delivery failed, no rows affected.");
            }
            return delivery;
        } catch (SQLException e) {
            throw new Exception("Error creating delivery: " + e.getMessage(), e);
        }
    }

    private DeliveryDTO mapResultSetToDeliveryDTO(ResultSet rs) throws Exception {
        // int deliveryId = rs.getInt("id");
        // int orderId = rs.getInt("order_id");
        String orderNumber;
        try {
            orderNumber = rs.getString("order_number");
            // int accountId = rs.getInt("account_id");
            int addressId = rs.getInt("address_id");
            java.sql.Date date = rs.getDate("delivery_date");
            String status = rs.getString("status");
            Date orderDate = rs.getDate("order_date");
            float orderPrice = rs.getFloat("total_price");

            // Cr�e les objets DTO partiels
            OrderDTO orderDTO = new OrderDTO(orderNumber, orderDate, orderPrice, null); // Adapte si besoin
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setId(addressId);

            DeliveryDTO delivery = new DeliveryDTO();
            delivery.setOrder(orderDTO);
            delivery.setAddress(addressDTO);
            delivery.setDeliveryDate(date);
            delivery.setDeliveryStatus(status);

            return delivery;
        } catch (SQLException e) {
            throw new Exception("Error mapping ResultSet to DeliveryDTO: " + e.getMessage(), e);
        }
    }

    public Delivery findById(int id, OrderDTO order) throws Exception {
        String sql = "SELECT * FROM deliveries WHERE id = ?";
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int orderId = rs.getInt("order_id");
                int addressId = rs.getInt("address_id");
                Date deliveryDate = rs.getDate("delivery_date");
                String status = rs.getString("status");

                AddressDTO address = toDTO(addressDAO.findById(addressId));

                return new Delivery(id, address, deliveryDate, status, order);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new Exception("Error finding delivery by ID: " + e.getMessage(), e);
        }
    }

    public void update(DeliveryDTO delivery) throws Exception {
        int orderId = orderDAO.findIdByOrderNumber(delivery.getOrder().getOrderNumber());

        String sql = "UPDATE deliveries SET address_id = ?, delivery_date = ?, status = ? WHERE order_id = ?";
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, delivery.getAddress().getId());
            stmt.setDate(2, delivery.getDeliveryDate());
            stmt.setString(3, delivery.getDeliveryStatus());
            stmt.setInt(4, orderId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating delivery failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new Exception("Error updating delivery: " + e.getMessage(), e);
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM deliveries WHERE id = ?";
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error deleting delivery: " + e.getMessage(), e);
        }
    }

    // R�cup�re toutes les livraisons avec un statut sp�cifique
    public List<DeliveryDTO> findByStatus(String status) throws Exception {
        String sql = """
                    SELECT d.*, o.order_number, o.account_id, o.order_date, o.total_price
                    FROM deliveries d
                    JOIN orders o ON d.order_id = o.id
                    WHERE d.status = ?
                """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            List<DeliveryDTO> deliveries = new ArrayList<>();
            while (rs.next()) {
                deliveries.add(mapResultSetToDeliveryDTO(rs));
            }
            return deliveries;
        } catch (SQLException e) {
            throw new Exception("Error retrieving deliveries by status: " + e.getMessage(), e);
        }
    }

    // R�cup�re toutes les livraisons par statut ET utilisateur
    public List<DeliveryDTO> findByStatusAndAccountId(String status, int accountId) throws Exception {
        String sql = """
                    SELECT d.*, o.order_number, o.account_id, o.order_date, o.total_price
                    FROM deliveries d
                    JOIN orders o ON d.order_id = o.id
                    WHERE d.status = ? AND o.account_id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, accountId);
            ResultSet rs = stmt.executeQuery();

            List<DeliveryDTO> deliveries = new ArrayList<>();
            while (rs.next()) {
                deliveries.add(mapResultSetToDeliveryDTO(rs));
            }
            return deliveries;
        } catch (SQLException e) {
            throw new Exception("Error retrieving deliveries by status and account ID: " + e.getMessage(), e);
        }
    }

    // R�cup�re toutes les livraisons dont le statut est diff�rent de celui donn�
    public List<DeliveryDTO> findByStatusNot(String status) throws Exception {
        String sql = """
                    SELECT d.*, o.order_number, o.account_id, o.order_date, o.total_price
                    FROM deliveries d
                    JOIN orders o ON d.order_id = o.id
                    WHERE d.status != ?
                """;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            List<DeliveryDTO> deliveries = new ArrayList<>();
            while (rs.next()) {
                deliveries.add(mapResultSetToDeliveryDTO(rs));
            }
            return deliveries;
        } catch (SQLException e) {
            throw new Exception("Error retrieving deliveries by status not equal to: " + e.getMessage(), e);
        }
    }

    public Delivery findByOrderNumber(String orderNumber) throws Exception {
        String sql = """
                    SELECT d.id, d.delivery_date, d.status,
                           d.address_id, d.order_id
                    FROM deliveries d
                    JOIN orders o ON d.order_id = o.id
                    WHERE o.order_number = ?
                """;

        Connection conn = DBConnection.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int deliveryId = rs.getInt("id");
                Date deliveryDate = rs.getDate("delivery_date");
                String status = rs.getString("status");

                int addressId = rs.getInt("address_id");
                int orderId = rs.getInt("order_id");

                AddressDTO addressDTO = toDTO(addressDAO.findById(addressId));
                OrderDTO orderDTO = orderDAO.findById(orderId);

                return new Delivery(deliveryId, addressDTO, deliveryDate, status, orderDTO);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new Exception("Error finding delivery by order number: " + e.getMessage(), e);
        }
    }

}
