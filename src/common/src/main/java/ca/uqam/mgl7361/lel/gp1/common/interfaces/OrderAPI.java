package ca.uqam.mgl7361.lel.gp1.common.interfaces;

import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.CartDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;


public interface OrderAPI {
    OrderDTO createOrder(AccountDTO account, CartDTO cart) throws Exception;

    OrderDTO findOrderByOrderNumber(String s) throws Exception;

}
