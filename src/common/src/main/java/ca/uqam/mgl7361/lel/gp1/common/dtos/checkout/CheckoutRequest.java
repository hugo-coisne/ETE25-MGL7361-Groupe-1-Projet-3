package ca.uqam.mgl7361.lel.gp1.common.dtos.checkout;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;

public record CheckoutRequest(AccountDTO accountDto, PaymentMethod paymentmethod, AddressDTO address) {

    public String toString() {
        return "CheckoutRequest(accountDto=" + accountDto + ", paymentmethod" + paymentmethod + ", address=" + address
                + ")";
    }
}
