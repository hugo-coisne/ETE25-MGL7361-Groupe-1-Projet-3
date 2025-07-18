package ca.uqam.mgl7361.lel.gp1.delivery.presentation;

import ca.uqam.mgl7361.lel.gp1.delivery.dto.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.dto.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import java.time.LocalDate;

public interface DeliveryAPI {
        DeliveryDTO createDelivery(AddressDTO address, LocalDate localDate, String inProgress, OrderDTO order);

        void updateStatusToDelivered(DeliveryDTO delivery);
}
