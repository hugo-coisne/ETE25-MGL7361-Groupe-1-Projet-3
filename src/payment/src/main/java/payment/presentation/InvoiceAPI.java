package payment.presentation;

import account.dto.AccountDTO;
import account.dto.CartDTO;
import payment.dto.PaymentMethod;
import payment.dto.InvoiceDTO;


public interface InvoiceAPI {
    public InvoiceDTO createInvoice(AccountDTO account, PaymentMethod paymentMethod) throws Exception;
}
