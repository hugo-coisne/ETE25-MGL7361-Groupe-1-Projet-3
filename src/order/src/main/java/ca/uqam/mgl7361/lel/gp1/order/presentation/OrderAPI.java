package ca.uqam.mgl7361.lel.gp1.order.presentation;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;
import ca.uqam.mgl7361.lel.gp1.order.dto.OrderDTO;


public interface OrderAPI {
    OrderDTO createOrder(AccountDTO account, CartDTO cart) throws Exception;

    OrderDTO findOrderByOrderNumber(String s) throws Exception;

}
