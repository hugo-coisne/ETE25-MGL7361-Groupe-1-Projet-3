package order.persistence;


import account.dto.AccountDTO;
import common.DBConnection;
import order.model.Order;
import shop.dto.BookDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OrderDAO {
    private final Logger logger = Logger.getLogger(OrderDAO.class.getName());

    private String generateOrderNumber() {
        LocalDate today = LocalDate.now();
        String datePart = today.format(DateTimeFormatter.BASIC_ISO_DATE);
        String uuidPart = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return datePart + "-" + uuidPart;
    }

    public Order createOrder(AccountDTO accountDTO, Map<BookDTO, Integer> books, double total_price) throws Exception {
        logger.log(Level.INFO, String.format("Creating order for account %s", accountDTO));

        String orderNumber = generateOrderNumber();
        LocalDate localDate = LocalDate.now(); // Utilise LocalDate pour la logique
        Order order;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(
                        "INSERT INTO orders (order_number, account_id, total_price, order_date) VALUES (?, ?, ?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, orderNumber);
            statement.setInt(2, accountDTO.getId());
            statement.setDouble(3, total_price);
            statement.setDate(4, Date.valueOf(localDate));

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("Order inserted successfully.");
            } else {
                throw new Exception("Error inserting order.");
            }

            order = new Order(
                    orderNumber,
                    localDate,
                    books
            );

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creating order", e);
            throw new Exception("Error creating order: " + e.getMessage(), e);
        }

        return order;
    }
}
