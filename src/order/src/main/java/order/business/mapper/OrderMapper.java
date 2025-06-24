package order.business.mapper;

import order.dto.OrderDTO;
import order.model.Order;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        return new OrderDTO(
                order.getOrderNumber(),
                order.getOrderDate(),
                order.getOrderPrice(),
                order.getItems()
        );
    }
}
