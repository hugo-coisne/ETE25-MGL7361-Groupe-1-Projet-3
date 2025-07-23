package ca.uqam.mgl7361.lel.gp1.delivery.presentation;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import java.util.Date;

public interface DeliveryAPI {
        DeliveryDTO createDelivery(AddressDTO address, Date date, String inProgress, OrderDTO order);

        void updateStatusToDelivered(DeliveryDTO delivery) throws Exception;
}
