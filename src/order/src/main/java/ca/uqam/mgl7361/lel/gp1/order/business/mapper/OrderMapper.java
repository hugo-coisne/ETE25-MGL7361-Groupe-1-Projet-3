package ca.uqam.mgl7361.lel.gp1.order.business.mapper;

import java.util.List;
import java.util.stream.Collectors;

import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderItemDTO;
import ca.uqam.mgl7361.lel.gp1.order.model.Order;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        List<OrderItemDTO> orderItemDTOs = order.getItems().entrySet().stream()
                .map(entry -> new OrderItemDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        OrderDTO orderDTO = new OrderDTO(
                order.getOrderNumber(),
                order.getOrderDate(),
                order.getOrderPrice(),
                orderItemDTOs);
        orderDTO.setId(order.getId());
        return orderDTO;
    }
}
