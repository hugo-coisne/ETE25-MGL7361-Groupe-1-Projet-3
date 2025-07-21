package ca.uqam.mgl7361.lel.gp1.order.business;

import ca.uqam.mgl7361.lel.gp1.order.dto.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.order.external.BookAPIImpl;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookProperty;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;
import ca.uqam.mgl7361.lel.gp1.order.business.mapper.OrderMapper;
import ca.uqam.mgl7361.lel.gp1.order.model.Order;
import ca.uqam.mgl7361.lel.gp1.order.persistence.OrderDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private static final Logger logger = LogManager.getLogger(OrderService.class);

    private final OrderDAO orderDAO;
    private final BookAPIImpl bookAPI;

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.bookAPI = new BookAPIImpl();
    }

    private Map<BookDTO, Integer> getBooksFromCart(CartDTO cart) throws Exception {
        logger.debug("Extracting books from cart: {}", cart.getBookIsbn());
        Map<BookDTO, Integer> result = new HashMap<>();

        for (Map.Entry<String, Integer> entry : cart.getBookIsbn().entrySet()) {
            String isbn = entry.getKey();
            int quantity = entry.getValue();
            logger.debug("Fetching book with ISBN: {} and quantity: {}", isbn, quantity);

            List<BookDTO> books = this.bookAPI.getBooksBy(Map.of(BookProperty.ISBN, isbn));

            if (books != null && !books.isEmpty()) {
                result.put(books.getFirst(), quantity);
                logger.debug("Book found: {}", books.getFirst().getTitle());
            } else {
                logger.warn("No book found for ISBN: {}", isbn);
            }
        }

        return result;
    }

    public OrderDTO createOrder(AccountDTO account, CartDTO cart) throws Exception {
        logger.info("Creating order for account: {}", account != null ? account.getEmail() : "null");

        if (account == null || cart == null) {
            logger.error("Account or cart is null");
            throw new IllegalArgumentException("Account and Cart cannot be null");
        }
        if (account.getId() <= 0) {
            logger.error("Invalid account ID: {}", account.getId());
            throw new IllegalArgumentException("Invalid account ID");
        }
        if (cart.getBookIsbn() == null || cart.getBookIsbn().isEmpty()) {
            logger.warn("Attempted to create order with empty cart for account: {}", account.getEmail());
            throw new IllegalArgumentException("Cart is empty");
        }

        Map<BookDTO, Integer> bookDTO = Map.of(new BookDTO(), 0);
        try {
            bookDTO = getBooksFromCart(cart);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        double totalPrice = 0.0;
        for (Map.Entry<BookDTO, Integer> entry : bookDTO.entrySet()) {
            BookDTO book = entry.getKey();
            int quantity = entry.getValue();
            double linePrice = book.getPrice() * quantity;
            totalPrice += linePrice;
        }

        logger.info("Total price for order: {}", totalPrice);
        Order order = this.orderDAO.createOrder(account, bookDTO, totalPrice);
        logger.info("Order created with order number: {}", order.getOrderNumber());

        return OrderMapper.toDTO(order);
    }

    public OrderDTO findOrderByOrderNumber(String orderNumber) throws Exception {
        logger.info("Looking for order with number: {}", orderNumber);

        if (orderNumber == null || orderNumber.isEmpty()) {
            logger.error("Invalid order number: null or empty");
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }

        int id = orderDAO.findIdByOrderNumber(orderNumber);
        logger.debug("Order ID resolved to: {}", id);

        OrderDTO order = this.orderDAO.findById(id);

        if (order == null) {
            logger.warn("Order not found for number: {}", orderNumber);
        } else {
            logger.info("Order retrieved successfully for number: {}", orderNumber);
        }

        return order;
    }
}
