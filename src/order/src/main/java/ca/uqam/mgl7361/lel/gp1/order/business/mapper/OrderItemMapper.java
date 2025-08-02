package ca.uqam.mgl7361.lel.gp1.order.business.mapper;

import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderItemDTO;
import ca.uqam.mgl7361.lel.gp1.order.model.OrderItem;

public class OrderItemMapper {

    public static OrderItem toModel(OrderItemDTO orderItemDTO) {
        return new OrderItem(
                orderItemDTO.getId(),
                orderItemDTO.getOrderNumber(),
                orderItemDTO.getBookIsbn(),
                orderItemDTO.getBookPrice(),
                orderItemDTO.getQuantity());

    }

    public static OrderItemDTO toDTO(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getOrderNumber(),
                orderItem.getBookIsbn(),
                orderItem.getBookPrice(),
                orderItem.getQuantity());
    }
}
