package delivery.business.mapper;

import delivery.dto.DeliveryDTO;
import delivery.model.Delivery;

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
