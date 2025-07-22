package ca.uqam.mgl7361.lel.gp1.order.presentation;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;

public interface OrderAPI {
    ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO createOrder(AccountDTO account, CartDTO cart) throws Exception;

    ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO findOrderByOrderNumber(String s) throws Exception;

}
