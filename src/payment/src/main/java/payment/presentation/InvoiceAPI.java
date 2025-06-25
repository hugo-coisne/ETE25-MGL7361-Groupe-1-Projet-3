package payment.presentation;

import order.dto.OrderDTO;
import payment.dto.PaymentMethod;
import payment.dto.InvoiceDTO;


public interface InvoiceAPI {
    public InvoiceDTO createInvoice(OrderDTO order, PaymentMethod paymentMethod) throws Exception;
}
