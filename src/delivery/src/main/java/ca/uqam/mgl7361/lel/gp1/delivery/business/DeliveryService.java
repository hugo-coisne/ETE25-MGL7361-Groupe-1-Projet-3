package ca.uqam.mgl7361.lel.gp1.delivery.business;

import ca.uqam.mgl7361.lel.gp1.delivery.dto.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.dto.DeliveryDTO;
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

    public DeliveryDTO createDelivery(AddressDTO address, Date deliveryDate, String deliveryStatus, OrderDTO order)
            throws Exception {
        DeliveryDTO deliveryDTO = new DeliveryDTO(address, deliveryDate, deliveryStatus, order);
        deliveryDAO.createDelivery(deliveryDTO, order);
        return deliveryDTO;
    }

    // public void updateDeliveryOrder(DeliveryDTO delivery, OrderDTO order) {
    //     delivery.setOrder(order);
    // }

    // public void updateDeliveryStatus(DeliveryDTO delivery, String newStatus) {
    //     delivery.setDeliveryStatus(newStatus);
    // }

    // public void updateDeliveryAddress(DeliveryDTO delivery, AddressDTO newAddress) {
    //     delivery.setAddress(newAddress);
    // }

    // public void updateDeliveryDate(DeliveryDTO delivery, Date newDate) {
    //     delivery.setDeliveryDate(newDate);
    // }

    public List<DeliveryDTO> getAllOrdersInTransit() throws Exception {
        return deliveryDAO.findByStatus("In Transit");
    }

    public List<DeliveryDTO> getAllOrdersInTransit(AccountDTO account) throws Exception {
        return deliveryDAO.findByStatusAndAccountId("In Transit", account.getId());
    }

    public List<DeliveryDTO> getAllOrdersDelivered() throws Exception {
        return deliveryDAO.findByStatus("Delivered");
    }

    // public List<DeliveryDTO> getAllOrdersNotDelivered() throws Exception {
    //     return deliveryDAO.findByStatusNot("Delivered");
    // }

    // public void updateStatusToInProgress(DeliveryDTO delivery) {
    //     delivery.setDeliveryStatus("In Progress");
    // }

    // public void updateStatusToInTransit(DeliveryDTO delivery) {
    //     delivery.setDeliveryStatus("In Transit");
    // }

    public void updateStatusToDelivered(DeliveryDTO delivery) throws Exception {
        delivery.setDeliveryStatus("Delivered");
        deliveryDAO.update(delivery);
    }

    // public void updateStatusToCanceled(DeliveryDTO delivery) {
    //     delivery.setDeliveryStatus("Canceled");
    // }

}
