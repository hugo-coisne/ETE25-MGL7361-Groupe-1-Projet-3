package ca.uqam.mgl7361.lel.gp1.order.business.mapper;

import ca.uqam.mgl7361.lel.gp1.order.dto.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.order.model.Order;

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
