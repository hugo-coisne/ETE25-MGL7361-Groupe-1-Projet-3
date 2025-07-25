package ca.uqam.mgl7361.lel.gp1.common.clients;

import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.InvoiceDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.PaymentRequest;
import feign.Headers;
import feign.RequestLine;

public interface CheckoutAPIClient {

    @RequestLine("POST /checkout")
    @Headers("Content-Type: application/json")
    InvoiceDTO checkout(PaymentRequest paymentRequest);
    
}
