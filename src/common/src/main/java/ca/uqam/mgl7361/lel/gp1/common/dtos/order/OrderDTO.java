package ca.uqam.mgl7361.lel.gp1.common.dtos.order;

import java.sql.Date;
import java.util.List;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a customer order containing multiple items.")
public class OrderDTO extends DTO {

    @Schema(description = "Unique order number assigned to the order", example = "ORD-20250728-001", required = true)
    private String orderNumber;

    @Schema(description = "Date the order was placed", example = "2025-07-28", required = true)
    private Date orderDate;

    @Schema(description = "Total price of the order", example = "89.99", required = true)
    private float orderPrice;

    @Schema(description = "List of ordered items", required = true)
    private List<OrderItemDTO> orderItemDTOs;

    @Schema(description = "Internal ID of the order", example = "123")
    private int id;

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
