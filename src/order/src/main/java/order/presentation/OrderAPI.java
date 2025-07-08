package order.presentation;

import account.dto.AccountDTO;
import account.dto.CartDTO;
import order.dto.OrderDTO;


public interface OrderAPI {
    OrderDTO createOrder(AccountDTO account, CartDTO cart) throws Exception;

    OrderDTO findOrderByOrderNumber(String s) throws Exception;

}
