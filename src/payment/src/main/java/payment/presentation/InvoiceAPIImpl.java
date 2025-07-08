package payment.presentation;

import account.dto.AccountDTO;
import account.dto.CartDTO;
import payment.dto.PaymentMethod;
import payment.business.InvoiceService;
import payment.dto.InvoiceDTO;

public class InvoiceAPIImpl implements InvoiceAPI {
    private final InvoiceService invoiceService;

    public InvoiceAPIImpl() {
        this.invoiceService = new InvoiceService();
    }

    public InvoiceDTO createInvoice(AccountDTO account, PaymentMethod paymentMethod) throws Exception {
        return this.invoiceService.createInvoice(account, paymentMethod);
    }
}
