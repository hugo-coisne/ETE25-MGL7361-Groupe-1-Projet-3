package ca.uqam.mgl7361.lel.gp1.delivery.business;

import ca.uqam.mgl7361.lel.gp1.common.clients.AccountAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.BookAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.CartAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.Clients;
import ca.uqam.mgl7361.lel.gp1.common.clients.OrderAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.persistence.DeliveryDAO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookStockQuantityRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private final DeliveryDAO deliveryDAO;
    private final AccountAPIClient accountAPIClient = Clients.accountClient;
    OrderAPIClient orderAPIClient = Clients.orderClient;
    CartAPIClient cartAPI = Clients.cartClient;
    BookAPIClient bookAPIClient = Clients.bookClient;

    Logger logger = LogManager.getLogger(DeliveryService.class);

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
        return deliveryDAO.findByStatus("Shipped");
    }

    public List<DeliveryDTO> getAllOrdersAwaitingShipping() throws Exception {
        return deliveryDAO.findByStatus("Awaiting shipping");
    }

    public List<DeliveryDTO> getAllOrdersInTransitFor(AccountDTO account) throws Exception {
        return deliveryDAO.findByStatusAndAccountId("Shipped", account.getId());
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

    public void updateStatusToShipped(DeliveryDTO delivery) throws Exception {
        delivery.setStatus("Shipped");
        deliveryDAO.update(delivery);
        CartDTO cartDTO = cartAPI.getCart(delivery.getAddress().getAccountId());
        cartDTO.getCartItemDtos().forEach(cartItemDTO -> {
            BookStockQuantityRequest bookStockQuantityRequest = new BookStockQuantityRequest(
                    cartItemDTO.book(), cartItemDTO.quantity());
            bookAPIClient.decreaseBookStockQuantity(bookStockQuantityRequest);
        });
        logger.info("Decreased book stock for cart items in delivery: " + delivery.getId());
        cartAPI.clearCart(cartDTO.getId());
    }

    public List<DeliveryDTO> getOrderStatusesFor(AccountDTO accountDTO) throws Exception {
        AccountDTO accountDTO2 = accountAPIClient
                .signin(Map.of("email", accountDTO.getEmail(), "password", accountDTO.getPassword()));
        return deliveryDAO.findByAccountId(accountDTO2.getId());
    }

    public void pass(int time) throws Exception { // to simulate the shipping of previous day orders
        int shippingDelay = 60 * 60 * 24;
        int deliveryDelay = 60 * 60 * 24 * 3;

        if (time >= deliveryDelay) {
            List<DeliveryDTO> ordersInTransit = getAllOrdersInTransit();
            for (int i = 0; i < ordersInTransit.size(); i++) {
                logger.info("setting " + ordersInTransit.get(i) + " to delivered");
                updateStatusToDelivered(ordersInTransit.get(i));
            }
            logger.info(ordersInTransit.size() + " orders delivered");
        }

        if (time >= shippingDelay) {
            List<DeliveryDTO> ordersWaitingToBeShipped = getAllOrdersAwaitingShipping();
            for (int i = 0; i < ordersWaitingToBeShipped.size(); i++) {
                updateStatusToShipped(ordersWaitingToBeShipped.get(i));
            }
        }
    }

    // public void updateStatusToCanceled(DeliveryDTO delivery) {
    // delivery.setStatus("Canceled");
    // }

}
