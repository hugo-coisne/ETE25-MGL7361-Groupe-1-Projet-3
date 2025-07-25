package ca.uqam.mgl7361.lel.gp1.payment.business;

import ca.uqam.mgl7361.lel.gp1.common.clients.AccountAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.BookAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.CartAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.BookAPIClient.BookQuantityRequest;
import ca.uqam.mgl7361.lel.gp1.common.clients.Clients;
import ca.uqam.mgl7361.lel.gp1.common.clients.OrderAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.OrderAPIClient.OrderRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.payment.business.mapper.InvoiceMapper;
import ca.uqam.mgl7361.lel.gp1.payment.model.Invoice;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.InvoiceDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.PaymentMethod;
import ca.uqam.mgl7361.lel.gp1.payment.persistence.InvoiceDAO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartItemDTO;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final InvoiceDAO invoiceDAO;

    public InvoiceService() {
        this.invoiceDAO = new InvoiceDAO();
    }

    public boolean isValidCart(CartDTO cart) {
        if (cart == null || cart.getCartItemDtos() == null) {
            return false;
        }
        BookAPIClient bookAPI = Clients.bookClient;
        for (CartItemDTO entry : cart.getCartItemDtos()) {
            BookDTO book = entry.book();
            Integer quantity = entry.quantity();
            if (!bookAPI.isSufficientlyInStock(new BookQuantityRequest(book, quantity))) {
                return false;
            }
        }
        return true;
    }

    private void sendEmail(String email, String subject, String body) {
        // Placeholder for email sending logic
        System.out.println("\u001B[32mSending email to: " + email + "\u001B[0m");
        System.out.println("\u001B[32mSubject: " + subject + "\u001B[0m");
        System.out.println("\u001B[32mBody: " + body + "\u001B[0m");
    }

    public InvoiceDTO createInvoice(AccountDTO account, PaymentMethod paymentMethod) throws Exception {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }

        AccountAPIClient accountAPI = Clients.accountClient;

        if (account == null) {
            throw new IllegalArgumentException("Account or cart cannot be null");
        }
        Map<String, String> credentials = Map.of("email", account.getEmail(), "password", account.getPassword());
        account = accountAPI.signin(credentials);

        CartAPIClient cartAPI = Clients.cartClient;
        CartDTO cart = cartAPI.getCart(account);
        if (!isValidCart(cart)) {
            throw new IllegalArgumentException("Cart is invalid or contains insufficient stock");
        }
        OrderAPIClient orderAPI = Clients.orderClient;
        OrderDTO order = orderAPI.createOrder(new OrderRequest(account, cart));

        Invoice invoice = invoiceDAO.createInvoice(
                order.getOrderNumber(),
                order.getOrderDate(),
                order.getOrderPrice(),
                paymentMethod);

        this.sendEmail(
                account.getEmail(),
                "LeL - new Invoice Created",
                "Your invoice has been created successfully.\n" +
                        "Invoice Number: " + invoice.getInvoiceNumber() + "\n" +
                        "Order Number: " + order.getOrderNumber() + "\n" +
                        "Total Price: " + invoice.getTotalPrice() + "\n" +
                        "Payment Method: " + paymentMethod.name());
        return InvoiceMapper.toDTO(invoice);
    }
}
