package ca.uqam.mgl7361.lel.gp1.common.dtos.payment;

public class PaymentMethodDTO {

    private String paymentMethod;

    public PaymentMethodDTO() {
    }

    public PaymentMethodDTO(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return this.paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String toString(){
        return "PaymentMethodDTO(paymentMethod="+paymentMethod+")";
    }

}
