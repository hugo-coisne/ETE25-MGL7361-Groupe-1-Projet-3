package ca.uqam.mgl7361.lel.gp1.common.dtos.payment;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;

public class InvoiceRequest extends DTO {
    private AccountDTO accountDTO;
    private PaymentMethod paymentMethod;

    public InvoiceRequest() {
    }

    public InvoiceRequest(AccountDTO accountDTO, PaymentMethod paymentMethod) {
        this.accountDTO = accountDTO;
        this.paymentMethod = paymentMethod;
    }

    public AccountDTO getAccountDTO() {
        return this.accountDTO;
    }

    public PaymentMethod getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
