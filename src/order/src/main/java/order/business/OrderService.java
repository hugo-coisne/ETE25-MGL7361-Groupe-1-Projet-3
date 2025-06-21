package order.business;

import account.business.entities.Account;
import account.business.entities.Cart;
import order.model.Order;
import shop.presentation.BookAPI;
import shop.presentation.BookAPIImpl;

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
