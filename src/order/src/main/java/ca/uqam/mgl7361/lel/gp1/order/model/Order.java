package ca.uqam.mgl7361.lel.gp1.order.model;


import ca.uqam.mgl7361.lel.gp1.shop.dto.BookDTO;

import java.sql.Date;
import java.util.Map;


public class Order {
    /*
     * Order class represents an order in the system.
     */
    private String orderNumber;
    private Date orderDate;
    private float orderPrice;
    private final Map<BookDTO, Integer> items; // Map of books to their quantities

    public Order(String orderNumber, Date orderDate, Map<BookDTO, Integer> items) {
        this.setOrderNumber(orderNumber);
        this.setOrderDate(orderDate);
        this.setOrderPrice(orderPrice);
        this.items = items;
    }

    // Setters
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }

    // Getters
    public String getOrderNumber() {
        return orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public Map<BookDTO, Integer> getItems() {
        return items;
    }

}
