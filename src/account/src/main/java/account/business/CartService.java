package account.business;

import java.util.logging.Logger;

import account.dto.AccountDTO;
import account.dto.CartDTO;
import account.exception.UnsufficientStockException;
import account.model.Cart;
import account.persistence.CartDAO;
import account.presentation.AccountAPI;
import account.presentation.AccountAPIImpl;
import shop.dto.BookDTO;
import shop.presentation.BookAPI;
import shop.presentation.BookAPIImpl;

public class CartService {

    static CartService instance;
    Logger logger = Logger.getLogger(CartService.class.getName());

    AccountAPI accountAPI = new AccountAPIImpl();
    BookAPI bookAPI = new BookAPIImpl();

    CartDAO cartDAO = new CartDAO();

    public static CartService getInstance() {
        if (instance == null) {
            instance = new CartService();
        }
        return instance;
    }

    public CartDTO getCart(AccountDTO accountDto) {
        logger.info("Retrieving cart for account: " + accountDto.getEmail());
        accountDto = accountAPI.signin(accountDto.getEmail(), accountDto.getPassword());
        Cart cart = cartDAO.getCart(accountDto);

        if (cart.getId() < 0 || cart.getTotalPrice() < 0) {
            logger.info("No cart found for account: " + accountDto.getEmail() + ", creating a new cart.");
            return cartDAO.createCartFor(accountDto).toDTO();
        }
        logger.info(cart.toString());
        CartDTO cartDto = cart.toDTO();
        logger.info(cartDto.toString());
        logger.info("Returning " + cartDto.toString());
        return cartDto;
    }

    public void addBookToCart(AccountDTO accountDto, BookDTO bookDto) throws UnsufficientStockException {
        accountDto = accountAPI.signin(accountDto.getEmail(), accountDto.getPassword());
        if (!bookAPI.isInStock(bookDto)) {
            logger.warning("Book is not in stock: " + bookDto.getTitle());
            throw new UnsufficientStockException("Book is not in stock: " + bookDto.getTitle());
        }
        logger.info("Adding book to cart for account: " + accountDto.getEmail() + ", Book: " + bookDto.getTitle());
        cartDAO.addBookToCart(accountDto, bookDto);
    }

    public void removeBookFromCart(AccountDTO accountDto, BookDTO bookDto) {
        accountDto = accountAPI.signin(accountDto.getEmail(), accountDto.getPassword());
        cartDAO.removeBookFromCart(accountDto.toAccount(), bookDto);
    }

    public void clearCart(AccountDTO accountDto) {
        accountDto = accountAPI.signin(accountDto.getEmail(), accountDto.getPassword());
        cartDAO.clearCart(accountDto.toAccount());
    }

}
