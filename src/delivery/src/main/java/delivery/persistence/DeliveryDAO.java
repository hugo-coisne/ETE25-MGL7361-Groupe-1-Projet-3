package delivery.persistence;

import common.DBConnection;
import delivery.dto.AddressDTO;
import delivery.dto.DeliveryDTO;
import delivery.model.Delivery;
import order.dto.OrderDTO;
import order.model.Order;
import delivery.model.Address;
import order.persistence.OrderDAO;
import shop.dto.BookDTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static delivery.business.mapper.AddressMapper.toDTO;

public class DeliveryDAO {
    private final OrderDAO orderDAO;
    private final AddressDAO addressDAO;

    public DeliveryDAO() throws SQLException {
        this.orderDAO = new OrderDAO();
        this.addressDAO = new AddressDAO();
    }

    public Delivery createDelivery(Delivery delivery) throws SQLException, Exception {
        int orderId = orderDAO.findIdByOrderNumber(delivery.getOrder().getOrderNumber());
        int addressId = delivery.getAddress().getId(); // ici, on suppose que AddressDTO a un id

        String sql = "INSERT INTO deliveries (order_id, address_id, delivery_date, status) VALUES (?, ?, ?, ?)";
        Connection connection = DBConnection.getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, addressId);
            stmt.setDate(3, Date.valueOf(delivery.getDeliveryDate()));
            stmt.setString(4, delivery.getDeliveryStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating delivery failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    delivery.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating delivery failed, no ID obtained.");
                }
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
        LocalDate orderDate = rs.getDate("order_date").toLocalDate();
        float orderPrice = rs.getFloat("total_price");

        // Crée les objets DTO partiels
        OrderDTO orderDTO = new OrderDTO(orderNumber, orderDate, orderPrice, null); // Adapte si besoin
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(addressId);

        DeliveryDTO delivery = new DeliveryDTO();
        delivery.setOrder(orderDTO);
        delivery.setAddress(addressDTO);
        delivery.setDeliveryDate(date.toLocalDate());
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
                    LocalDate deliveryDate = rs.getDate("delivery_date").toLocalDate();
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

    public void update(Delivery delivery) throws SQLException, Exception {
        int orderId = orderDAO.findIdByOrderNumber(delivery.getOrder().getOrderNumber());

        String sql = "UPDATE deliveries SET order_id = ?, address_id = ?, delivery_date = ?, status = ? WHERE id = ?";
        Connection connection = DBConnection.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, delivery.getAddress().getId());
            stmt.setDate(3, Date.valueOf(delivery.getDeliveryDate()));
            stmt.setString(4, delivery.getDeliveryStatus());
            stmt.setInt(5, delivery.getId());

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

    // Récupère toutes les livraisons avec un statut spécifique
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

    // Récupère toutes les livraisons par statut ET utilisateur
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

    // Récupère toutes les livraisons dont le statut est différent de celui donné
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

}
