package ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.presentation;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.dto.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.dto.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.order.dto.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.business.DeliveryService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class DeliveryAPIImpl {

    private final DeliveryService deliveryService;

    public DeliveryAPIImpl() throws SQLException {
        this.deliveryService = new DeliveryService();
    }

    public DeliveryDTO createDelivery(AddressDTO address, Date date, String deliveryStatus, OrderDTO order) throws Exception {
        return deliveryService.createDelivery(address, date, deliveryStatus, order);
    }

    public void updateStatusToDelivered(DeliveryDTO delivery) throws Exception {
        deliveryService.updateStatusToDelivered(delivery);
    }

    public List<DeliveryDTO> getAllOrdersInTransit() throws SQLException {
        return deliveryService.getAllOrdersInTransit();
    }

    public List<DeliveryDTO> getAllOrdersInTransit(AccountDTO account) throws SQLException {
        return deliveryService.getAllOrdersInTransit(account);
    }

    public List<DeliveryDTO> getAllOrdersDelivered() throws SQLException {
        return deliveryService.getAllOrdersDelivered();
    }
}
