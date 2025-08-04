package ca.uqam.mgl7361.lel.gp1.common.clients;

import java.util.List;

import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface OrderAPIClient {
    @RequestLine("POST /orders")
    @Headers("Content-Type: application/json")
    OrderDTO createOrder(OrderRequest request);

    @RequestLine("GET /orders/number/{orderId}")
    @Headers("Accept: application/json")
    OrderDTO getOrderByNumber(@Param("orderId") String orderId);

    @RequestLine("GET /orders/id/{orderId}")
    @Headers("Accept: application/json")
    OrderDTO getOrderById(@Param("orderId") int orderId);

    @RequestLine("POST /orders/account")
    @Headers("Content-Type: application/json")
    List<OrderDTO> getOrdersFor(AccountDTO accountDTO);
}
