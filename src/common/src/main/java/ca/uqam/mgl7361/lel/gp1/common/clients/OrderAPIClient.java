package ca.uqam.mgl7361.lel.gp1.common.clients;

import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface OrderAPIClient {
    @RequestLine("POST /orders")
    @Headers("Content-Type: application/json")
    OrderDTO createOrder(OrderRequest request);

    @RequestLine("GET /orders/{orderId}")
    @Headers("Accept: application/json")
    OrderDTO getOrderById(@Param("orderId") String orderId);

    record OrderRequest(AccountDTO account, CartDTO cart) {
    }
}
