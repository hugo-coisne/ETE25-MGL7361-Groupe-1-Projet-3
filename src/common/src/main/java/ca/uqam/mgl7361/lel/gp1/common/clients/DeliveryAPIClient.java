package ca.uqam.mgl7361.lel.gp1.common.clients;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.CreateDeliveryRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import feign.Headers;
import feign.RequestLine;

import java.sql.Time;
import java.util.List;

public interface DeliveryAPIClient {

    @RequestLine("POST /deliveries/create")
    @Headers("Content-Type: application/json")
    DeliveryDTO createDelivery(CreateDeliveryRequest request);

    @RequestLine("PUT /deliveries/delivered")
    @Headers("Content-Type: application/json")
    void updateStatusToDelivered(DeliveryDTO delivery);

    @RequestLine("GET /deliveries/in-transit")
    @Headers("Accept: application/json")
    List<DeliveryDTO> getAllOrdersInTransit();

    @RequestLine("GET /deliveries/delivered")
    @Headers("Accept: application/json")
    List<DeliveryDTO> getAllDeliveredOrders();

    @RequestLine("GET /deliveries/status")
    @Headers("Accept: application/json")
    DeliveryDTO getOrderStatusFor(OrderDTO order);

    @RequestLine("GET /deliveries/stati")
    @Headers("Accept: application/json")
    List<DeliveryDTO> getOrderStatiFor(AccountDTO accountDto);

    @RequestLine("GET /deliveries/passTime")
    @Headers("Accept: application/json")
    void pass(Time time);
}
