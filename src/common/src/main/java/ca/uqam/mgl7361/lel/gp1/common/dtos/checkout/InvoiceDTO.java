package ca.uqam.mgl7361.lel.gp1.common.dtos.checkout;

import java.sql.Date;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "InvoiceDTO", description = "Données représentant une facture")
public class InvoiceDTO extends DTO {

    @Schema(description = "Numéro unique de la facture", example = "INV123456")
    private String invoiceNumber;

    @Schema(description = "Date de la facture", example = "2025-07-28")
    private Date invoiceDate;

    @Schema(description = "Prix total facturé", example = "199.99")
    private float totalPrice;

    @Schema(description = "Méthode de paiement utilisée")
    private PaymentMethod paymentmethod;

    @Schema(description = "Numéro de la commande associée", example = "ORD987654")
    private String orderNumber;

    public InvoiceDTO(String invoiceNumber, String orderNumber, Date invoiceDate, float totalPrice,
            PaymentMethod paymentmethod) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.totalPrice = totalPrice;
        this.paymentmethod = paymentmethod;
        this.orderNumber = orderNumber;
    }

    public InvoiceDTO() {
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

    public void setPaymentmethod(PaymentMethod paymentmethod) {
        this.paymentmethod = paymentmethod;
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

    public PaymentMethod getPaymentmethod() {
        return paymentmethod;
    }

    public String getOrderNumber() {
        return orderNumber;
    }
}
