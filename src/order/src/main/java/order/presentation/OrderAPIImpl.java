package order.presentation;

import account.model.Account;
import account.model.Cart;
import order.business.OrderService;
import order.model.Order;

public class OrderAPIImpl implements OrderAPI {
    private final OrderService orderService = OrderService.getInstance();

    public Order createOrder(Account account, Cart cart) {
        return this.orderService.createOrder(account, cart);
    }
}
