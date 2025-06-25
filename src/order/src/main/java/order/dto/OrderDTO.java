package order.dto;

import shop.dto.BookDTO;


import java.sql.Date;
import java.util.Map;

public class OrderDTO {
    private final String orderNumber;
    private final Date orderDate;
    private final float orderPrice;
    private final Map<BookDTO, Integer> items; // Map of books to their quantities

    public OrderDTO(
            String orderNumber,
            Date orderDate,
            float orderPrice,
            Map<BookDTO, Integer> items
    ) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
        this.items = items;
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

    public Map<BookDTO, Integer> getItems() {
        return items;
    }
}
