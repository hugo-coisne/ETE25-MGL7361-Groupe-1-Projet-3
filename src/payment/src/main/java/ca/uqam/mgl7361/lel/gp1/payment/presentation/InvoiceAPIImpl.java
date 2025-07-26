package ca.uqam.mgl7361.lel.gp1.payment.presentation;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.payment.business.InvoiceService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.InvoiceDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.PaymentMethod;

public class InvoiceAPIImpl implements InvoiceAPI {
    private final InvoiceService invoiceService;

    public InvoiceAPIImpl() {
        this.invoiceService = new InvoiceService();
    }

    public InvoiceDTO createInvoice(AccountDTO account, PaymentMethod paymentMethod, OrderDTO orderDTO) throws Exception {
        return this.invoiceService.createInvoice(account, paymentMethod, orderDTO);
    }
}
