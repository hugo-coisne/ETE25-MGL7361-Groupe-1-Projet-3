package ca.uqam.mgl7361.lel.gp1.order.business.mapper;

import java.util.List;
import java.util.stream.Collectors;

import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderItemDTO;
import ca.uqam.mgl7361.lel.gp1.order.model.Order;
import ca.uqam.mgl7361.lel.gp1.order.model.OrderItem;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        List<OrderItemDTO> orderItemDTOs = order.getItems().stream()
                .map(OrderItemMapper::toDTO)
                .collect(Collectors.toList());

        OrderDTO orderDTO = new OrderDTO(
                order.getOrderNumber(),
                order.getOrderDate(),
                order.getOrderPrice(),
                orderItemDTOs);
        orderDTO.setId(order.getId());
        orderDTO.setAccountId(order.getAccountId());
        return orderDTO;
    }

    public static Order toModel(OrderDTO orderDTO) {
        List<OrderItem> orderItemDTOs = orderDTO.getItems().stream()
                .map(OrderItemMapper::toModel)
                .collect(Collectors.toList());

        Order order = new Order(
                orderDTO.getOrderNumber(),
                orderDTO.getOrderDate(),
                orderItemDTOs);
        order.setOrderPrice(orderDTO.getOrderPrice());
        order.setId(order.getId());
        return order;
    }
}
