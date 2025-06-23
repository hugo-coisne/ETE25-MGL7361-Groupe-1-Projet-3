package order.presentation;

import account.model.Cart;
import order.model.Order;
import account.model.Account;

public interface OrderAPI {
    Order createOrder(Account account, Cart cart);

}
