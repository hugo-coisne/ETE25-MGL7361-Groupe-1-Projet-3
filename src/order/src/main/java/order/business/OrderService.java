package order.business;

import account.business.entities.Account;
import account.business.entities.Cart;
import order.models.Order;
import shop.presentation.BookAPI;
import shop.presentation.BookAPIImpl;

public class OrderService {
    private static OrderService instance = null;

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    public Order createOrder(Account account, Cart cart) {
//        throw new UnsupportedOperationException("Not implemented yet");

        BookAPI bookAPI = new BookAPIImpl();

        // Get the books from the isbn of the cart and build the Map of books to their quantities

        for (String isbn : cart.getItems().keySet()) {
            // Get the book from the API
            var book = bookAPI.getBooksBy(isbn);
            if (book == null) {
                throw new IllegalArgumentException("Book with ISBN " + isbn + " not found.");
            }
            // Add the book to the order
            cart.addItem(book, cart.getItems().get(isbn));
        }

    }
}
