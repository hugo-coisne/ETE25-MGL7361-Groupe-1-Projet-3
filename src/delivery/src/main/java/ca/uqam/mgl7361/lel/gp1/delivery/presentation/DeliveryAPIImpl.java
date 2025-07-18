package ca.uqam.mgl7361.lel.gp1.delivery.presentation;

import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.account.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.business.DeliveryService;
import ca.uqam.mgl7361.lel.gp1.delivery.dto.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.dto.DeliveryDTO;

import java.sql.Date;
import java.util.List;

public class DeliveryAPIImpl {

    private final DeliveryService deliveryService;

    public DeliveryAPIImpl() throws Exception {
        this.deliveryService = new DeliveryService();
    }

    public DeliveryDTO createDelivery(AddressDTO address, Date date, String deliveryStatus, OrderDTO order)
            throws Exception {
        return deliveryService.createDelivery(address, date, deliveryStatus, order);
    }

    public void updateStatusToDelivered(DeliveryDTO delivery) throws Exception {
        deliveryService.updateStatusToDelivered(delivery);
    }

    public List<DeliveryDTO> getAllOrdersInTransit() throws Exception {
        return deliveryService.getAllOrdersInTransit();
    }

    public List<DeliveryDTO> getAllOrdersInTransit(AccountDTO account) throws Exception {
        return deliveryService.getAllOrdersInTransit(account);
    }

    public List<DeliveryDTO> getAllOrdersDelivered() throws Exception {
        return deliveryService.getAllOrdersDelivered();
    }
}
