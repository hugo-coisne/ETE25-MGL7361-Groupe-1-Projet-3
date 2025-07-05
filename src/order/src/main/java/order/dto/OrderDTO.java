package order.dto;

import shop.dto.BookDTO;


import java.time.LocalDate;
import java.util.Map;

public class OrderDTO {
    private final String orderNumber;
    private final LocalDate orderDate;
    private final float orderPrice;
    private final Map<BookDTO, Integer> items; // Map of books to their quantities

    public OrderDTO(
            String orderNumber,
            LocalDate orderDate,
            float orderPrice,
            Map<BookDTO, Integer> items
    ) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
        this.items = items;
    }

    public OrderDTO() {
        // Default constructor for serialization/deserialization
        this.orderNumber = null;
        this.orderDate = null;
        this.orderPrice = 0.0f;
        this.items = null;
    }

    // GETTERS ----------------------------------------------------------------
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
