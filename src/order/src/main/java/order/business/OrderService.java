package order.business;

import account.model.Account;
import account.model.Cart;
import order.model.Order;


public class OrderService {
    private static OrderService instance = null;

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    public Order createOrder(Account account, Cart cart) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
