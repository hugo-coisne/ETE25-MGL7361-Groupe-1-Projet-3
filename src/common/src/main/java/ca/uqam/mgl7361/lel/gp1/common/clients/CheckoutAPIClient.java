package ca.uqam.mgl7361.lel.gp1.common.clients;

import ca.uqam.mgl7361.lel.gp1.common.dtos.checkout.CheckoutDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.checkout.CheckoutRequest;
import feign.Headers;
import feign.RequestLine;

public interface CheckoutAPIClient {

    @RequestLine("POST /checkout")
    @Headers("Content-Type: application/json")
    CheckoutDTO checkout(CheckoutRequest checkoutRequest);

}
