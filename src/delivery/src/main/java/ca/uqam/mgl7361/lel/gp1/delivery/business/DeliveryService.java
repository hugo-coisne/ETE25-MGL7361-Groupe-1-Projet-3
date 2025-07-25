package ca.uqam.mgl7361.lel.gp1.delivery.business;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.persistence.DeliveryDAO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;

import java.util.Date;
import java.util.List;

public class DeliveryService {

    private final DeliveryDAO deliveryDAO;

    public DeliveryService() {
        this.deliveryDAO = new DeliveryDAO();
    }

    public DeliveryService(DeliveryDAO deliveryDAO) {
        this.deliveryDAO = deliveryDAO;
    }

    public DeliveryDTO createDelivery(AddressDTO address, Date date, String status, OrderDTO order)
            throws Exception {
        DeliveryDTO deliveryDTO = new DeliveryDTO(address, date, status, order);
        deliveryDAO.createDelivery(deliveryDTO, order);
        return deliveryDTO;
    }

    // public void updateDeliveryOrder(DeliveryDTO delivery, OrderDTO order) {
    // delivery.setOrder(order);
    // }

    // public void updateStatus(DeliveryDTO delivery, String newStatus) {
    // delivery.setStatus(newStatus);
    // }

    // public void updateDeliveryAddress(DeliveryDTO delivery, AddressDTO
    // newAddress) {
    // delivery.setAddress(newAddress);
    // }

    // public void updateDate(DeliveryDTO delivery, Date newDate) {
    // delivery.setDate(newDate);
    // }

    public List<DeliveryDTO> getAllOrdersInTransit() throws Exception {
        return deliveryDAO.findByStatus("In Transit");
    }

    public List<DeliveryDTO> getAllOrdersInTransit(AccountDTO account) throws Exception {
        return deliveryDAO.findByStatusAndAccountId("In Transit", account.getId());
    }

    public List<DeliveryDTO> getAllDeliveredOrders() throws Exception {
        return deliveryDAO.findByStatus("Delivered");
    }

    // public List<DeliveryDTO> getAllOrdersNotDelivered() throws Exception {
    // return deliveryDAO.findByStatusNot("Delivered");
    // }

    // public void updateStatusToInProgress(DeliveryDTO delivery) {
    // delivery.setStatus("In Progress");
    // }

    // public void updateStatusToInTransit(DeliveryDTO delivery) {
    // delivery.setStatus("In Transit");
    // }

    public void updateStatusToDelivered(DeliveryDTO delivery) throws Exception {
        delivery.setStatus("Delivered");
        deliveryDAO.update(delivery);
    }

    // public void updateStatusToCanceled(DeliveryDTO delivery) {
    // delivery.setStatus("Canceled");
    // }

}
