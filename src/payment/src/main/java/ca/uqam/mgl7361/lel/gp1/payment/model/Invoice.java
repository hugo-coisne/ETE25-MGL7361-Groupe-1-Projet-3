package ca.uqam.mgl7361.lel.gp1.payment.model;

import ca.uqam.mgl7361.lel.gp1.payment.dto.PaymentMethod;

import java.sql.Date;

public class Invoice {
    private int id;
    private String invoiceNumber;
    private Date invoiceDate;
    private float totalPrice;
    private PaymentMethod paymentMethod;
    private String orderNumber;

    public Invoice(int id, String invoiceNumber, String orderNumber, Date invoiceDate, float totalPrice, PaymentMethod paymentMethod) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.orderNumber = orderNumber;
    }

    // SETTERS ----------------------------------------------------------------

    public void setId(int id) {
        this.id = id;
    }

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

    public int getId() {
        return id;
    }

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
