package payment.business;

import account.dto.AccountDTO;
import account.dto.CartDTO;
import account.presentation.AccountAPI;
import account.presentation.AccountAPIImpl;
import account.presentation.CartAPIImpl;
import order.dto.OrderDTO;
import order.presentation.OrderAPIImpl;
import payment.dto.PaymentMethod;
import payment.business.mapper.InvoiceMapper;
import payment.model.Invoice;
import payment.dto.InvoiceDTO;
import payment.persistence.InvoiceDAO;
import shop.dto.BookDTO;
import shop.presentation.BookAPIImpl;

import java.util.Map;

public class InvoiceService {
    private final InvoiceDAO invoiceDAO;

    public InvoiceService() {
        this.invoiceDAO = new InvoiceDAO();
    }

    public boolean isValidCart(CartDTO cart) {
        if (cart == null || cart.getBooksDto() == null) {
            return false;
        }
        BookAPIImpl bookAPI = new BookAPIImpl();
        for (Map.Entry<BookDTO, Integer> entry : cart.getBooksDto().entrySet()) {
            BookDTO book = entry.getKey();
            Integer quantity = entry.getValue();
            if (!bookAPI.isSufficientlyInStock(book, quantity)) {
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

        AccountAPI accountAPI = new AccountAPIImpl();

        if (account == null) {
            throw new IllegalArgumentException("Account or cart cannot be null");
        }
        account = accountAPI.signin(account.getEmail(), account.getPassword());

        CartAPIImpl cartAPI = new CartAPIImpl();
        CartDTO cart = cartAPI.getCart(account);
        if (!isValidCart(cart)) {
            throw new IllegalArgumentException("Cart is invalid or contains insufficient stock");
        }
        OrderAPIImpl orderAPI = new OrderAPIImpl();
        OrderDTO order = orderAPI.createOrder(account, cart);

        Invoice invoice = invoiceDAO.createInvoice(
                order.getOrderNumber(),
                order.getOrderDate(),
                order.getOrderPrice(),
                paymentMethod
        );

        this.sendEmail(
                account.getEmail(),
                "LeL - new Invoice Created",
                "Your invoice has been created successfully.\n" +
                        "Invoice Number: " + invoice.getInvoiceNumber() + "\n" +
                        "Order Number: " + order.getOrderNumber() + "\n" +
                        "Total Price: " + invoice.getTotalPrice() + "\n" +
                        "Payment Method: " + paymentMethod.name()
        );
        return InvoiceMapper.toDTO(invoice);
    }
}
