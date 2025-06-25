package payment.presentation;

import order.dto.OrderDTO;
import payment.dto.PaymentMethod;
import payment.business.InvoiceService;
import payment.dto.InvoiceDTO;

public class InvoiceAPIImpl implements InvoiceAPI {
    private final InvoiceService invoiceService;

    public InvoiceAPIImpl() {
        this.invoiceService = new InvoiceService();
    }

    public InvoiceDTO createInvoice(OrderDTO order, PaymentMethod paymentMethod) throws Exception {
        return this.invoiceService.createInvoice(order, paymentMethod);
    }
}
