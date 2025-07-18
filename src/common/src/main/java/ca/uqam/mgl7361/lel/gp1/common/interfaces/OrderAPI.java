package ca.uqam.mgl7361.lel.gp1.common.interfaces;

import ca.uqam.mgl7361.lel.gp1.common.dtos.account.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.account.CartDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;


public interface OrderAPI {
    OrderDTO createOrder(AccountDTO account, CartDTO cart) throws Exception;

    OrderDTO findOrderByOrderNumber(String s) throws Exception;

}
