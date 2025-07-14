package ca.uqam.mgl7361.lel.gp1.order.business;


import ca.uqam.mgl7361.lel.gp1.order.dto.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.account.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.account.dto.CartDTO;
import ca.uqam.mgl7361.lel.gp1.order.business.mapper.OrderMapper;
import ca.uqam.mgl7361.lel.gp1.order.model.Order;
import ca.uqam.mgl7361.lel.gp1.order.persistence.OrderDAO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookProperty;
import ca.uqam.mgl7361.lel.gp1.shop.presentation.BookAPIImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderService {
    private final OrderDAO orderDAO;
    private final BookAPIImpl bookAPI;

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.bookAPI = new BookAPIImpl();
    }

    private Map<BookDTO, Integer> getBooksFromCart(CartDTO cart) throws Exception {
        Map<BookDTO, Integer> result = new HashMap<>();

        for (Map.Entry<String, Integer> entry : cart.getBookIsbn().entrySet()) {
            String isbn = entry.getKey();
            int quantity = entry.getValue();

            List<BookDTO> books = this.bookAPI.getBooksBy(
                    Map.of(BookProperty.ISBN, isbn)
            );

            if (books != null && !books.isEmpty()) {
                result.put(books.getFirst(), quantity);
            }
        }

        return result;
    }

    public OrderDTO createOrder(AccountDTO account, CartDTO cart) throws Exception {
        if (account == null || cart == null) {
            throw new IllegalArgumentException("Account and Cart cannot be null");
        }
        if (account.getId() <= 0) {
            throw new IllegalArgumentException("Invalid account ID");
        }
        if (cart.getBookIsbn() == null || cart.getBookIsbn().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        Map<BookDTO, Integer> bookDTO = getBooksFromCart(cart);
        double totalPrice = 0.0;
        for (Map.Entry<BookDTO, Integer> entry : bookDTO.entrySet()) {
            BookDTO book = entry.getKey();
            int quantity = entry.getValue();
            totalPrice += book.getPrice() * quantity;
        }
        Order order = this.orderDAO.createOrder(account, bookDTO, totalPrice);
        return OrderMapper.toDTO(order);
    }

    public OrderDTO findOrderByOrderNumber(String orderNumber) throws Exception {
        if (orderNumber == null || orderNumber.isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }

        OrderDTO order = this.orderDAO.findById(orderDAO.findIdByOrderNumber(orderNumber));
        if (order == null) {
            return null; // or throw an exception if preferred
        }
        return order;
    }

}
