package ca.uqam.mgl7361.lel.gp1.common.dtos.checkout;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CheckoutRequest", description = "Request data for performing a checkout operation")
public record CheckoutRequest(
        @Schema(description = "Account information for the checkout", required = true) AccountDTO accountDto,

        @Schema(description = "Payment method used for the checkout", required = true) PaymentMethod paymentmethod,

        @Schema(description = "Delivery address for the checkout", required = true) AddressDTO address) {
    @Override
    public String toString() {
        return "CheckoutRequest(accountDto=" + accountDto + ", paymentmethod=" + paymentmethod + ", address=" + address
                + ")";
    }
}
