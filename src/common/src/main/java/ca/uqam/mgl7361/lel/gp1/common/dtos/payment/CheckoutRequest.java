package ca.uqam.mgl7361.lel.gp1.common.dtos.payment;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;

public record CheckoutRequest(AccountDTO accountDto, PaymentMethod paymentMethod, AddressDTO address) {

    public String toString() {
        return "CheckoutRequest(accountDto=" + accountDto + ", paymentMethod" + paymentMethod + ", address=" + address
                + ")";
    }
}
