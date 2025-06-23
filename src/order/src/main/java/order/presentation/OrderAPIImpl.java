package order.presentation;

import account.dto.AccountDTO;
import account.dto.CartDTO;
import order.business.OrderService;
import order.dto.OrderDTO;

public class OrderAPIImpl implements OrderAPI {
    private final OrderService orderService;

    public OrderAPIImpl() {
        this.orderService = new OrderService();
    }

    public OrderDTO createOrder(AccountDTO account, CartDTO cart) throws Exception {

        return this.orderService.createOrder(account, cart);
    }
}
