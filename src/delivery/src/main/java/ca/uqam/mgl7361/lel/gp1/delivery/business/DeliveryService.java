package ca.uqam.mgl7361.lel.gp1.delivery.business;

import ca.uqam.mgl7361.lel.gp1.common.clients.AccountAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.BookAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.CartAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.Clients;
import ca.uqam.mgl7361.lel.gp1.common.clients.OrderAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.business.mapper.DeliveryMapper;
import ca.uqam.mgl7361.lel.gp1.delivery.model.Delivery;
import ca.uqam.mgl7361.lel.gp1.delivery.persistence.DeliveryDAO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookStockQuantityRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.CartDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;

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
        Delivery delivery = new Delivery(0, address.getId(), date, status, order.getId());
        deliveryDAO.createDelivery(delivery);
        return DeliveryMapper.toDTO(delivery);
    }

    public List<DeliveryDTO> getAllOrdersInTransit() throws Exception {
        return DeliveryMapper.toDTO(deliveryDAO.findByStatus("Shipped"));
    }

    public List<DeliveryDTO> getAllOrdersAwaitingShipping() throws Exception {
        return DeliveryMapper.toDTO(deliveryDAO.findByStatus("Awaiting shipping"));
    }

    public List<DeliveryDTO> getAllOrdersInTransitFor(AccountDTO account) throws Exception {
        return DeliveryMapper.toDTO(deliveryDAO.findByStatusAndAccountId("Shipped", account.getId()));
    }

    public List<DeliveryDTO> getAllDeliveredOrders() throws Exception {
        return DeliveryMapper.toDTO(deliveryDAO.findByStatus("Delivered"));
    }

    public void updateStatusToDelivered(DeliveryDTO delivery) throws Exception {
        delivery.setStatus("Delivered");
        deliveryDAO.update(DeliveryMapper.toModel(delivery));
    }

    public void updateStatusToShipped(DeliveryDTO delivery) throws Exception {
        delivery.setStatus("Shipped");
        deliveryDAO.update(DeliveryMapper.toModel(delivery));

        OrderDTO orderDTO = orderAPIClient.getOrderById(delivery.getOrderId());
        logger.info("Got order (by id) : " + orderDTO);
        int accountId = orderDTO.getAccountId();

        logger.info("Getting cart for accountId " + accountId);

        CartDTO cartDTO = cartAPI.getCart(accountId);
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
        List<Delivery> deliveries = deliveryDAO.findByAccountId(accountDTO2.getId());
        return DeliveryMapper.toDTO(deliveries);
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

}
