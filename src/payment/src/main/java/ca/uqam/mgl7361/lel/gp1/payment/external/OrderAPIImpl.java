package ca.uqam.mgl7361.lel.gp1.payment.external;

import ca.uqam.mgl7361.lel.gp1.common.dtos.account.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.account.CartDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.interfaces.OrderAPI;

public class OrderAPIImpl implements OrderAPI {

    @Override
    public OrderDTO createOrder(AccountDTO account, CartDTO cart) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createOrder'");
    }

    @Override
    public OrderDTO findOrderByOrderNumber(String s) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findOrderByOrderNumber'");
    }

}
