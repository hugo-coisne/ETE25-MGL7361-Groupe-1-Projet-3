package ca.uqam.mgl7361.lel.gp1.payment.presentation;

import ca.uqam.mgl7361.lel.gp1.payment.dto.PaymentMethod;
import ca.uqam.mgl7361.lel.gp1.common.dtos.account.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.payment.business.InvoiceService;
import ca.uqam.mgl7361.lel.gp1.payment.dto.InvoiceDTO;

public class InvoiceAPIImpl implements InvoiceAPI {
    private final InvoiceService invoiceService;

    public InvoiceAPIImpl() {
        this.invoiceService = new InvoiceService();
    }

    public InvoiceDTO createInvoice(AccountDTO account, PaymentMethod paymentMethod) throws Exception {
        return this.invoiceService.createInvoice(account, paymentMethod);
    }
}
