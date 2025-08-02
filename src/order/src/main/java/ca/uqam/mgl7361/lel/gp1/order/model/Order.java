package ca.uqam.mgl7361.lel.gp1.order.model;

import java.sql.Date;
import java.util.List;

import ca.uqam.mgl7361.lel.gp1.common.dtos.Printable;

public class Order extends Printable{
    /*
     * Order class represents an order in the system.
     */
    private String orderNumber;
    private int accountId;
    private Date orderDate;
    private float orderPrice;
    private List<OrderItem> items; // Map of books to their quantities
    private int id;

    public Order(String orderNumber, Date orderDate, List<OrderItem> items) {
        this.setOrderNumber(orderNumber);
        this.setOrderDate(orderDate);
        this.setOrderPrice(orderPrice);
        this.items = items;
    }

    public Order(String orderNumber, Date orderDate) {
        this.setOrderNumber(orderNumber);
        this.setOrderDate(orderDate);
    }

    // Setters
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getAccountId() {
        return this.accountId;
    }

    public void setItems(List<OrderItem> orderItems) {
        this.items = orderItems;
    }

}
