package payment.business;

import order.dto.OrderDTO;
import payment.dto.PaymentMethod;
import payment.business.mapper.InvoiceMapper;
import payment.model.Invoice;
import payment.dto.InvoiceDTO;
import payment.persistence.InvoiceDAO;

public class InvoiceService {
    private final InvoiceDAO invoiceDAO;

    public InvoiceService() {
        this.invoiceDAO = new InvoiceDAO();
    }

    public InvoiceDTO createInvoice(OrderDTO order, PaymentMethod paymentMethod) throws Exception {
        if (order == null) {
            throw new IllegalArgumentException("order is null");
        }

        if (paymentMethod == null) {
            throw new IllegalArgumentException("paymentMethod is null");
        }

        if (order.getOrderDate() == null) {
            throw new IllegalArgumentException("order.getOrderDate is null");
        }

        if (order.getOrderNumber() == null) {
            throw new IllegalArgumentException("order.getOrderNumber is null");
        }

        if (order.getOrderPrice() < 0.0) {
            throw new IllegalArgumentException("order.getOrderPrice is negative");
        }

        Invoice invoice = this.invoiceDAO.createInvoice(
                order.getOrderNumber(),
                order.getOrderDate(),
                order.getOrderPrice(),
                paymentMethod
        );
        return InvoiceMapper.toDTO(invoice);
    }
}
