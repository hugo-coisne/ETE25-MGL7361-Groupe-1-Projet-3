package ca.uqam.mgl7361.lel.gp1.common.dtos.payment;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;

public record PaymentRequest(AccountDTO accountDto, PaymentMethod paymentMethod, AddressDTO address) {

    public String toString() {
        return "PaymentRequest(accountDto=" + accountDto + ", paymentMethod" + paymentMethod + ", address=" + address
                + ")";
    }
}
