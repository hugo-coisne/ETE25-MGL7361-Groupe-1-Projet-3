package ca.uqam.mgl7361.lel.gp1.payment.business.mapper;

import ca.uqam.mgl7361.lel.gp1.payment.model.Invoice;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.InvoiceDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.PaymentMethod;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.PaymentMethodDTO;

public class InvoiceMapper {
    public static Invoice toModel(InvoiceDTO invoiceDTO) {
        return new Invoice(
                -1,
                invoiceDTO.getInvoiceNumber(),
                invoiceDTO.getOrderNumber(),
                invoiceDTO.getInvoiceDate(),
                invoiceDTO.getTotalPrice(),
                PaymentMethod.valueOf(invoiceDTO.getPaymentMethod().getPaymentMethod()));
    }

    public static InvoiceDTO toDTO(Invoice invoice) {
        return new InvoiceDTO(
                invoice.getInvoiceNumber(),
                invoice.getOrderNumber(),
                invoice.getInvoiceDate(),
                invoice.getTotalPrice(),
                new PaymentMethodDTO(invoice.getPaymentMethod().toString()));
    }
}
