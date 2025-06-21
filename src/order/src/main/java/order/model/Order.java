package order.model;



import java.time.Instant;
import java.util.Map;

import shop.model.Book;

public class Order {
    /*
     * Order class represents an order in the system.
     */
    private String orderNumber;
    private Instant orderDate;
    private float orderPrice;
    private final Map<Book, Integer> items; // Map of books to their quantities

    public Order(String orderNumber, Instant orderDate, Map<Book, Integer> items) {
        this.setOrderNumber(orderNumber);
        this.setOrderDate(orderDate);
        this.setOrderPrice(orderPrice);
        this.items = items;
    }

    // Setters
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }

    // Getters
    public String getOrderNumber() {
        return orderNumber;
    }

    public Instant getOrderDate() {
        return orderDate;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public Map<Book, Integer> getItems() {
        return items;
    }

}
