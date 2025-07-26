package ca.uqam.mgl7361.lel.gp1.payment.business;

import ca.uqam.mgl7361.lel.gp1.common.clients.AccountAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.AddressAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.BookAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.CartAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.BookAPIClient.BookQuantityRequest;
import ca.uqam.mgl7361.lel.gp1.common.clients.Clients;
import ca.uqam.mgl7361.lel.gp1.common.clients.DeliveryAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.OrderAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.OrderAPIClient.OrderRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.CreateDeliveryRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.payment.business.mapper.InvoiceMapper;
import ca.uqam.mgl7361.lel.gp1.payment.model.Invoice;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.CheckoutDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.InvoiceDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.PaymentMethod;
import ca.uqam.mgl7361.lel.gp1.payment.persistence.InvoiceDAO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartItemDTO;

import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    private final InvoiceDAO invoiceDAO;
    AddressAPIClient addressAPIClient = Clients.addressClient;
    AccountAPIClient accountAPIClient = Clients.accountClient;
    DeliveryAPIClient deliveryAPIClient = Clients.deliveryClient;
    CartAPIClient cartAPI = Clients.cartClient;
    OrderAPIClient orderAPI = Clients.orderClient;

    private Logger logger = LogManager.getLogger(InvoiceService.class);

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

    // private void sendEmail(String email, String subject, String body) {
    // // Placeholder for email sending logic
    // System.out.println("\u001B[32mSending email to: " + email + "\u001B[0m");
    // System.out.println("\u001B[32mSubject: " + subject + "\u001B[0m");
    // System.out.println("\u001B[32mBody: " + body + "\u001B[0m");
    // }

    public AccountDTO authenticate(AccountDTO accountDTO) {

        if (accountDTO == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        Map<String, String> credentials = Map.of(
                "email", accountDTO.getEmail(),
                "password", accountDTO.getPassword());

        return accountAPIClient.signin(credentials);
    }

    public CartDTO getCart(AccountDTO accountDTO) {
        CartDTO cart = cartAPI.getCart(accountDTO);

        if (!isValidCart(cart)) {
            throw new IllegalArgumentException("Cart is invalid or contains insufficient stock");
        }
        return cart;
    }

    public AddressDTO registerAddressForAccount(AddressDTO addressDTO, AccountDTO accountDTO) {
        addressDTO.setAccountId(accountDTO.getId());
        return addressAPIClient.create(addressDTO);
    }

    public CheckoutDTO checkout(AccountDTO accountDTO, PaymentMethod paymentMethod, AddressDTO addressDTO)
            throws Exception {

        accountDTO = authenticate(accountDTO);

        CartDTO cart = getCart(accountDTO);

        addressDTO = registerAddressForAccount(addressDTO, accountDTO);

        OrderDTO order = orderAPI.createOrder(new OrderRequest(accountDTO, cart));

        logger.debug("order obtained : " + order);

        InvoiceDTO invoice = createInvoice(accountDTO, paymentMethod, order);

        DeliveryDTO deliveryDTO = deliveryAPIClient
                .createDelivery(new CreateDeliveryRequest(addressDTO, new Date(), "Awaiting shipping", order));

        return new CheckoutDTO(invoice, deliveryDTO);
    }

    public InvoiceDTO createInvoice(AccountDTO account, PaymentMethod paymentMethod, OrderDTO order) throws Exception {

        try {
            PaymentMethod.valueOf(paymentMethod.toString().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new Exception("invalid payment method", e);
        }

        Invoice invoice = invoiceDAO.createInvoice(
                order.getOrderNumber(),
                order.getOrderDate(),
                order.getOrderPrice(),
                paymentMethod);

        // this.sendEmail(
        // account.getEmail(),
        // "LeL - new Invoice Created",
        // "Your invoice has been created successfully.\n" +
        // "Invoice Number: " + invoice.getInvoiceNumber() + "\n" +
        // "Order Number: " + order.getOrderNumber() + "\n" +
        // "Total Price: " + invoice.getTotalPrice() + "\n" +
        // "Payment Method: " + paymentMethod.name());
        return InvoiceMapper.toDTO(invoice);
    }
}
