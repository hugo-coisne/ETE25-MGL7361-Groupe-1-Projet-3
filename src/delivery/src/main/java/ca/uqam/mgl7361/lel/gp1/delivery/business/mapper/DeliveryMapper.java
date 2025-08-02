package ca.uqam.mgl7361.lel.gp1.delivery.business.mapper;

import java.util.List;
import java.util.stream.Collectors;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.model.Delivery;

public class DeliveryMapper {
    public static DeliveryDTO toDTO(Delivery delivery) {
        return new DeliveryDTO(
                delivery.getId(),
                delivery.getAddressId(),
                delivery.getDate(),
                delivery.getStatus(),
                delivery.getOrderId());
    }

    public static Delivery toModel(DeliveryDTO deliveryDTO) {
        return new Delivery(
                deliveryDTO.getId(),
                deliveryDTO.getAddressId(),
                deliveryDTO.getDate(),
                deliveryDTO.getStatus(),
                deliveryDTO.getOrderId());
    }

    public static List<Delivery> toModel(List<DeliveryDTO> deliveryDTOs) {
        return deliveryDTOs.stream().map(entry -> DeliveryMapper.toModel(entry)).collect(Collectors.toList());
    }

    public static List<DeliveryDTO> toDTO(List<Delivery> deliveryDTOs) {
        return deliveryDTOs.stream().map(entry -> DeliveryMapper.toDTO(entry)).collect(Collectors.toList());
    }
}
