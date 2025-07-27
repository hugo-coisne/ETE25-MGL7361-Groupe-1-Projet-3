package ca.uqam.mgl7361.lel.gp1.checkout.presentation;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.checkout.InvoiceDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.checkout.PaymentMethod;

public interface CheckoutAPI {
    public InvoiceDTO createInvoice(AccountDTO account, PaymentMethod paymentmethod, OrderDTO orderDTO)
            throws Exception;
}
