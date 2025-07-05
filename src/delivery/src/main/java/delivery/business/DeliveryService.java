package delivery.business;

import account.dto.AccountDTO;
import delivery.dto.AddressDTO;
import delivery.dto.DeliveryDTO;
import order.dto.OrderDTO;
import delivery.persistence.DeliveryDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DeliveryService {

    private final DeliveryDAO deliveryDAO;

    public DeliveryService() throws SQLException {
        this.deliveryDAO = new DeliveryDAO();
    }

    public DeliveryDTO createDelivery(AddressDTO address, LocalDate deliveryDate, String deliveryStatus, OrderDTO order) {
        return new DeliveryDTO(address, deliveryDate, deliveryStatus, order);
    }

    public void updateDeliveryOrder(DeliveryDTO delivery, OrderDTO order) {
        delivery.setOrder(order);
    }

    public void updateDeliveryStatus(DeliveryDTO delivery, String newStatus) {
        delivery.setDeliveryStatus(newStatus);
    }

    public void updateDeliveryAddress(DeliveryDTO delivery, AddressDTO newAddress) {
        delivery.setAddress(newAddress);
    }

    public void updateDeliveryDate(DeliveryDTO delivery, LocalDate newDate) {
        delivery.setDeliveryDate(newDate);
    }



    public DeliveryService(DeliveryDAO deliveryDAO) {
        this.deliveryDAO = deliveryDAO;
    }

    public List<DeliveryDTO> getAllOrdersInTransit() throws SQLException {
        return deliveryDAO.findByStatus("In Transit");
    }

    public List<DeliveryDTO> getAllOrdersInTransit(AccountDTO account) throws SQLException {
        return deliveryDAO.findByStatusAndAccountId("In Transit", account.getId());
    }

    public List<DeliveryDTO> getAllOrdersDelivered() throws SQLException {
        return deliveryDAO.findByStatus("Delivered");
    }

    public List<DeliveryDTO> getAllOrdersNotDelivered() throws SQLException {
        return deliveryDAO.findByStatusNot("Delivered");
    }

    public void updateStatusToInProgress(DeliveryDTO delivery) {
        delivery.setDeliveryStatus("In Progress");
    }

    public void updateStatusToInTransit(DeliveryDTO delivery) {
        delivery.setDeliveryStatus("In Transit");
    }

    public void updateStatusToDelivered(DeliveryDTO delivery) throws Exception {
        delivery.setDeliveryStatus("Delivered");
        deliveryDAO.update(delivery);
    }

    public void updateStatusToCanceled(DeliveryDTO delivery) {
        delivery.setDeliveryStatus("Canceled");
    }


}
