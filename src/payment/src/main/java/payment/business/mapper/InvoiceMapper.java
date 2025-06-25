package payment.business.mapper;

import payment.model.Invoice;
import payment.dto.InvoiceDTO;

public class InvoiceMapper {
    public static Invoice toModel(InvoiceDTO invoiceDTO) {
        return new Invoice(
                -1,
                invoiceDTO.getInvoiceNumber(),
                invoiceDTO.getOrderNumber(),
                invoiceDTO.getInvoiceDate(),
                invoiceDTO.getTotalPrice(),
                invoiceDTO.getPaymentMethod()
        );
    }

    public static InvoiceDTO toDTO(Invoice invoice) {
        return new InvoiceDTO(
                invoice.getInvoiceNumber(),
                invoice.getOrderNumber(),
                invoice.getInvoiceDate(),
                invoice.getTotalPrice(),
                invoice.getPaymentMethod()
        );
    }
}
