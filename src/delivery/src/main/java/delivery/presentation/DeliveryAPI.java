package delivery.presentation;

import delivery.dto.AddressDTO;
import delivery.dto.DeliveryDTO;
import order.dto.OrderDTO;
import java.time.LocalDate;

public interface DeliveryAPI {
        DeliveryDTO createDelivery(AddressDTO address, LocalDate localDate, String inProgress, OrderDTO order);

        void updateStatusToDelivered(DeliveryDTO delivery);
}
