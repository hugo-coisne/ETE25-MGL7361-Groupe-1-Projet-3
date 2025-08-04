package ca.uqam.mgl7361.lel.gp1.order.persistence;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.order.DBConnection;
import ca.uqam.mgl7361.lel.gp1.order.model.Order;
import ca.uqam.mgl7361.lel.gp1.order.model.OrderItem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderDAO {
    private final Logger logger = LogManager.getLogger(OrderDAO.class.getName());

    private String generateOrderNumber() {
        LocalDate today = LocalDate.now();
        String datePart = today.format(DateTimeFormatter.BASIC_ISO_DATE);
        String uuidPart = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return datePart + "-" + uuidPart;
    }

    public Order createOrder(AccountDTO accountDTO, List<OrderItem> books, double total_price) throws Exception {
        logger.debug(String.format("Creating order for account %s", accountDTO));

        String orderNumber = generateOrderNumber();
        Date date = new Date(System.currentTimeMillis());
        Order order;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement insertOrder = conn.prepareStatement(
                        "INSERT INTO orders (order_number, account_id, total_price, order_date) VALUES (?, ?, ?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS);
                PreparedStatement insertContent = conn.prepareStatement(
                        "INSERT INTO order_contents (" +
                                "order_number, book_isbn, book_price, quantity) " +
                                "VALUES (?, ?, ?, ?)")) {
            conn.setAutoCommit(false);

            // Insert order
            insertOrder.setString(1, orderNumber);
            insertOrder.setInt(2, accountDTO.getId());
            insertOrder.setDouble(3, total_price);
            insertOrder.setDate(4, date);
            insertOrder.executeUpdate();

            ResultSet keys = insertOrder.getGeneratedKeys();
            int orderId = -1;
            if (keys.next()) {
                orderId = keys.getInt(1);
            } else {
                throw new SQLException("Id not generated for order");
            }

            // Insert contents
            for (OrderItem orderItem : books) {

                insertContent.setString(1, orderNumber);

                insertContent.setString(2, orderItem.getBookIsbn());

                if (orderItem.getBookPrice() != 0.0) {
                    insertContent.setDouble(3, orderItem.getBookPrice());
                } else {
                    insertContent.setNull(3, java.sql.Types.DECIMAL);
                }

                insertContent.setInt(4, orderItem.getQuantity());

                insertContent.addBatch(); // Add to batch for order contents
            }

            insertContent.executeBatch(); // Execute batch insert for order contents
            conn.commit(); // Commit the transaction

            order = new Order(orderNumber, date, books);
            order.setOrderPrice((float) total_price);
            order.setId(orderId);
            logger.debug("Order and order contents inserted successfully.");
            return order;
        } catch (SQLException e) {
            logger.error("Error creating order", e);
            throw new Exception("Error creating order: " + e.getMessage(), e);
        }

    }

    public int findIdByOrderNumber(String orderNumber) throws Exception {
        String sql = "SELECT id FROM orders WHERE order_number = ?";
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    logger.warn("No order found with order number: " + orderNumber);
                    return 0;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error finding order id by order number: " + e.getMessage(), e);
        }
    }

    public Order findById(int orderId) throws Exception {
        String sql = "SELECT id, order_number, account_id, order_date, total_price FROM orders WHERE id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String orderNumber = rs.getString("order_number");
                    java.sql.Date orderDate = rs.getDate("order_date");
                    float orderPrice = (float) rs.getDouble("total_price");
                    Order order = new Order(orderNumber, orderDate, null);
                    order.setOrderPrice(orderPrice);
                    order.setAccountId(rs.getInt("account_id"));
                    return order;
                } else {
                    logger.debug("No order found with id: " + orderId);
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error fetching order by ID: " + e.getMessage(), e);
        }
    }

    public List<OrderItem> getOrderItems(Order order) throws Exception {
        String sql = "SELECT * FROM order_contents WHERE order_number=?";
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getOrderNumber());
            ResultSet rs = stmt.executeQuery();
            List<OrderItem> orderItems = new ArrayList<>();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setBookIsbn(rs.getString("book_isbn"));
                orderItem.setBookPrice(rs.getFloat("book_price"));
                orderItem.setId(rs.getInt("id"));
                orderItem.setQuantity(rs.getInt("quantity"));
                orderItems.add(orderItem);
            }
            return orderItems;
        } catch (SQLException e) {
            logger.error(e);
            throw new Exception("Error finding order id by order number: " + e.getMessage(), e);
        }
    }

    public Order findByOrderNumber(String orderNumber) throws Exception {
        String sql = "SELECT id, account_id, order_date, total_price FROM orders WHERE order_number = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orderNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    java.sql.Date orderDate = rs.getDate("order_date");
                    float orderPrice = (float) rs.getDouble("total_price");
                    Order order = new Order(orderNumber, orderDate);
                    order.setOrderPrice(orderPrice);
                    order.setId(id);
                    return order;
                } else {
                    logger.debug("No order found with number : " + orderNumber);
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error fetching order by ID: " + e.getMessage(), e);
        }
    }

    public List<Order> getFor(int accountId) throws Exception {
        String sql = "SELECT * FROM orders WHERE account_id=?";
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            List<Order> orders = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order(
                        rs.getString("order_number"),
                        rs.getDate("order_date"));
                order.setAccountId(accountId);
                order.setId(rs.getInt("id"));
                order.setOrderPrice(rs.getFloat("total_price"));
                orders.add(order);
            }
            logger.debug("returning " + orders + "\n for accountId " + accountId);
            return orders;
        } catch (SQLException e) {
            logger.error(e);
            throw new Exception("Error finding order id by order number: " + e.getMessage(), e);
        }
    }

}
