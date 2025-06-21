package order.presentation;

import account.business.entities.Account;
import account.business.entities.Cart;
import order.business.OrderService;
import order.model.Order;

public class OrderAPIImpl implements OrderAPI {
    private final OrderService orderService = OrderService.getInstance();

    public Order createOrder(Account account, Cart cart) {
        return this.orderService.createOrder(account, cart);
    }
}
