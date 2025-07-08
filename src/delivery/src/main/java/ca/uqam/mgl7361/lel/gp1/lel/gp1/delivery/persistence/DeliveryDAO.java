package ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.persistence;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.DBConnection;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.dto.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.dto.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.model.Delivery;
import ca.uqam.mgl7361.lel.gp1.order.dto.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.order.persistence.OrderDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.business.mapper.AddressMapper.toDTO;

public class DeliveryDAO {
    private final OrderDAO orderDAO;
    private final AddressDAO addressDAO;

    public DeliveryDAO() throws SQLException {
        this.orderDAO = new OrderDAO();
        this.addressDAO = new AddressDAO();
    }

    public DeliveryDTO createDelivery(DeliveryDTO delivery) throws SQLException, Exception {
        int orderId = orderDAO.findIdByOrderNumber(delivery.getOrder().getOrderNumber());
        int addressId = delivery.getAddress().getId(); // ici, on suppose que AddressDTO a un id

        String sql = "INSERT INTO deliveries (order_id, address_id, delivery_date, status) VALUES (?, ?, ?, ?)";
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, addressId);
            stmt.setDate(3, delivery.getDeliveryDate());
            stmt.setString(4, delivery.getDeliveryStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating delivery failed, no rows affected.");
            }
        }
        return delivery;
    }

    private DeliveryDTO mapResultSetToDeliveryDTO(ResultSet rs) throws SQLException {
        int deliveryId = rs.getInt("id");
        int orderId = rs.getInt("order_id");
        String orderNumber = rs.getString("order_number");
        int accountId = rs.getInt("account_id");
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
    }


    public Delivery findById(int id) throws SQLException, Exception {
        String sql = "SELECT * FROM deliveries WHERE id = ?";
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    int addressId = rs.getInt("address_id");
                    Date deliveryDate = rs.getDate("delivery_date");
                    String status = rs.getString("status");

                    OrderDTO order = orderDAO.findById(orderId);
                    AddressDTO address = toDTO(addressDAO.findById(addressId));

                    return new Delivery(id, address, deliveryDate, status, order);
                } else {
                    return null;
                }
            }
        }
    }

    public void update(DeliveryDTO delivery) throws SQLException, Exception {
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
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM deliveries WHERE id = ?";
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // R�cup�re toutes les livraisons avec un statut sp�cifique
    public List<DeliveryDTO> findByStatus(String status) throws SQLException {
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
        }
    }

    // R�cup�re toutes les livraisons par statut ET utilisateur
    public List<DeliveryDTO> findByStatusAndAccountId(String status, int accountId) throws SQLException {
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
        }
    }

    // R�cup�re toutes les livraisons dont le statut est diff�rent de celui donn�
    public List<DeliveryDTO> findByStatusNot(String status) throws SQLException {
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
        }
    }

    public Delivery findByOrderNumber(String orderNumber) throws SQLException, Exception {
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
            try (ResultSet rs = stmt.executeQuery()) {
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
            }
        }
    }

}
