package ca.uqam.mgl7361.lel.gp1.payment.business.mapper;

import ca.uqam.mgl7361.lel.gp1.payment.model.Invoice;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.InvoiceDTO;

public class InvoiceMapper {
    public static Invoice toModel(InvoiceDTO invoiceDTO) {
        return new Invoice(
                -1,
                invoiceDTO.getInvoiceNumber(),
                invoiceDTO.getOrderNumber(),
                invoiceDTO.getInvoiceDate(),
                invoiceDTO.getTotalPrice(),
                invoiceDTO.getPaymentMethod());
    }

    public static InvoiceDTO toDTO(Invoice invoice) {
        return new InvoiceDTO(
                invoice.getInvoiceNumber(),
                invoice.getOrderNumber(),
                invoice.getInvoiceDate(),
                invoice.getTotalPrice(),
                invoice.getPaymentMethod());
    }
}
