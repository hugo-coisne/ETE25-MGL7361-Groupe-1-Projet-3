package ca.uqam.mgl7361.lel.gp1.order.presentation;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.dto.CartDTO;
import ca.uqam.mgl7361.lel.gp1.order.business.OrderService;
import ca.uqam.mgl7361.lel.gp1.order.dto.OrderDTO;

public class OrderAPIImpl implements OrderAPI {
    private final OrderService orderService;

    public OrderAPIImpl() {
        this.orderService = new OrderService();
    }

    public OrderDTO createOrder(AccountDTO account, CartDTO cart) throws Exception {
        return this.orderService.createOrder(account, cart);
    }

    public OrderDTO findOrderByOrderNumber(String orderId) throws Exception {
        return this.orderService.findOrderByOrderNumber(orderId);
    }
}
