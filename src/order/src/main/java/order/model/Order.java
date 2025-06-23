package order.model;


import shop.dto.BookDTO;

import java.time.LocalDate;
import java.util.Map;


public class Order {
    /*
     * Order class represents an order in the system.
     */
    private String orderNumber;
    private LocalDate orderDate;
    private float orderPrice;
    private final Map<BookDTO, Integer> items; // Map of books to their quantities

    public Order(String orderNumber, LocalDate orderDate, Map<BookDTO, Integer> items) {
        this.setOrderNumber(orderNumber);
        this.setOrderDate(orderDate);
        this.setOrderPrice(orderPrice);
        this.items = items;
    }

    // Setters
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }

    // Getters
    public String getOrderNumber() {
        return orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public Map<BookDTO, Integer> getItems() {
        return items;
    }

}
