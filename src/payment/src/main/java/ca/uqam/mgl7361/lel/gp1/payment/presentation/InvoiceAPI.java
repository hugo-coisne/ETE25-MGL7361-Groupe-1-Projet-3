package ca.uqam.mgl7361.lel.gp1.payment.presentation;

import ca.uqam.mgl7361.lel.gp1.payment.dto.PaymentMethod;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.payment.dto.InvoiceDTO;


public interface InvoiceAPI {
    public InvoiceDTO createInvoice(AccountDTO account, PaymentMethod paymentMethod) throws Exception;
}
