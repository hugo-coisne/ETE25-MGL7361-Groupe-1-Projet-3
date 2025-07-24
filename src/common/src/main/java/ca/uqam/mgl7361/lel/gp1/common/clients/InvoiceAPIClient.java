package ca.uqam.mgl7361.lel.gp1.common.clients;

import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.InvoiceDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.PaymentMethod;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import feign.Headers;
import feign.RequestLine;

public interface InvoiceAPIClient {

    @RequestLine("POST /invoices")
    @Headers("Content-Type: application/json")
    InvoiceDTO createInvoice(InvoiceRequest request);

    @RequestLine("POST /invoices/debug")
    @Headers("Content-Type: application/json")
    void debugInvoice(String rawJson); // ou Map<String, Object> si tu veux le typer

    public record InvoiceRequest(AccountDTO account, PaymentMethod paymentMethod){}
}
