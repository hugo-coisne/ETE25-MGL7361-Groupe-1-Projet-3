package ca.uqam.mgl7361.lel.gp1.common.dtos.order;

import java.sql.Date;
import java.util.List;

public class OrderDTO {
    private String orderNumber;
    private Date orderDate;
    private float orderPrice;
    private List<OrderItemDTO> orderItemDTOs; // Map of books to their quantities
    private int id; // Assuming there's an ID field for the order

    public OrderDTO(
            String orderNumber,
            Date orderDate,
            float orderPrice,
            List<OrderItemDTO> items) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
        this.orderItemDTOs = items;
    }

    public OrderDTO() {
        // Default constructor for serialization/deserialization
        this.orderNumber = null;
        this.orderDate = null;
        this.orderPrice = 0.0f;
        this.orderItemDTOs = null;
    }

    // GETTERS ----------------------------------------------------------------
    public String getOrderNumber() {
        return orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public List<OrderItemDTO> getItems() {
        return orderItemDTOs;
    }

    public int getId() {
        return id;
    }

    public int setId(int id) {
        this.id = id;
        return this.id;
    }

    public void setItems(List<OrderItemDTO> orderItemDTOs) {
        this.orderItemDTOs = orderItemDTOs;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }

}
