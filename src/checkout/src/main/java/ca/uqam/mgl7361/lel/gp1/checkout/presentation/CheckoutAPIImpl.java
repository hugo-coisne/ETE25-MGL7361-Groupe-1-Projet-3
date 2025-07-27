package ca.uqam.mgl7361.lel.gp1.checkout.presentation;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.checkout.business.CheckoutService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.checkout.InvoiceDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.checkout.PaymentMethod;

public class CheckoutAPIImpl implements CheckoutAPI {
    private CheckoutService checkoutService;

    public CheckoutAPIImpl() {
        this.checkoutService = new CheckoutService();
    }

    public InvoiceDTO createInvoice(AccountDTO account, PaymentMethod paymentmethod, OrderDTO orderDTO)
            throws Exception {
        return this.checkoutService.createInvoice(account, paymentmethod, orderDTO);
    }
}
