package payment.dto;

import java.sql.Date;

public class InvoiceDTO {
    private String invoiceNumber;
    private Date invoiceDate;
    private float totalPrice;
    private PaymentMethod paymentMethod;
    private String orderNumber;

    public InvoiceDTO(String invoiceNumber, String orderNumber, Date invoiceDate, float totalPrice, PaymentMethod paymentMethod) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.orderNumber = orderNumber;
    }

    // SETTERS ----------------------------------------------------------------

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    // GETTERS ----------------------------------------------------------------

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
}
