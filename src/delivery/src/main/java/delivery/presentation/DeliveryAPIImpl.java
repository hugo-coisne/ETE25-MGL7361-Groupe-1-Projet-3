package delivery.presentation;

import account.dto.AccountDTO;
import delivery.dto.AddressDTO;
import delivery.dto.DeliveryDTO;
import order.dto.OrderDTO;
import delivery.business.DeliveryService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DeliveryAPIImpl {

    private final DeliveryService deliveryService;

    public DeliveryAPIImpl() throws SQLException {
        this.deliveryService = new DeliveryService();
    }

    public DeliveryDTO createDelivery(AddressDTO address, LocalDate localDate, String deliveryStatus, OrderDTO order) {
        return deliveryService.createDelivery(address, localDate, deliveryStatus, order);
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
