package order.presentation;

import account.business.entities.Cart;
import order.model.Order;
import account.business.entities.Account;

public interface OrderAPI {
    Order createOrder(Account account, Cart cart);

}
