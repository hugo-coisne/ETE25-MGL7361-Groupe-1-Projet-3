package ca.uqam.mgl7361.lel.gp1.common.dtos.checkout;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "InvoiceRequest", description = "Requête contenant les informations nécessaires pour générer une facture")
public class InvoiceRequest extends DTO {

    @Schema(description = "Informations du compte client", required = true)
    private AccountDTO accountDTO;

    @Schema(description = "Méthode de paiement utilisée", required = true)
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
