package ca.uqam.mgl7361.lel.gp1.delivery.business.mapper;

import ca.uqam.mgl7361.lel.gp1.delivery.dto.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.model.Delivery;

public class DeliveryMapper {
    public static DeliveryDTO toDTO(Delivery delivery) {
        return new DeliveryDTO(
                delivery.getAddress(),
                delivery.getDeliveryDate(),
                delivery.getDeliveryStatus(),
                delivery.getOrder()
        );
    }
}
