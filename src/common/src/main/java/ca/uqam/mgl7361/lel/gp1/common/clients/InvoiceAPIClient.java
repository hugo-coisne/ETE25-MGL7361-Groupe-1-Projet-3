package ca.uqam.mgl7361.lel.gp1.common.clients;

import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.InvoiceDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.InvoiceRequest;
import feign.Headers;
import feign.RequestLine;

public interface InvoiceAPIClient {

    @RequestLine("POST /invoices")
    @Headers("Content-Type: application/json")
    InvoiceDTO createInvoice(InvoiceRequest invoiceRequest);
}
