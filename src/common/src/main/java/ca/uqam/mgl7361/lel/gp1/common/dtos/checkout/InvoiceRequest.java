package ca.uqam.mgl7361.lel.gp1.common.dtos.checkout;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;

public class InvoiceRequest extends DTO {
    private AccountDTO accountDTO;
    private PaymentMethod paymentmethod;

    public InvoiceRequest() {
    }

    public InvoiceRequest(AccountDTO accountDTO, PaymentMethod paymentmethod) {
        this.accountDTO = accountDTO;
        this.paymentmethod = paymentmethod;
    }

    public AccountDTO getAccountDTO() {
        return this.accountDTO;
    }

    public PaymentMethod getPaymentmethod() {
        return this.paymentmethod;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public void setPaymentmethod(PaymentMethod paymentmethod) {
        this.paymentmethod = paymentmethod;
    }
}
