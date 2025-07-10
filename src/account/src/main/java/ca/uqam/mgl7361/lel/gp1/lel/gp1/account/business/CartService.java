package ca.uqam.mgl7361.lel.gp1.lel.gp1.account.business;

import java.util.logging.Logger;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.apis.BookAPIImpl;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.dto.CartDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.exception.InvalidCartException;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.exception.UnsufficientStockException;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.model.Cart;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.persistence.CartDAO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.presentation.AccountAPI;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.presentation.AccountAPIImpl;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.dtos.shop.BookDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.interfaces.BookAPI;

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
        try {
            accountDto = accountAPI.signin(accountDto.getEmail(), accountDto.getPassword());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Cart cart = cartDAO.getCart(accountDto);

        if (cart.getId() < 0 || cart.getTotalPrice() < 0) {
            logger.info("No cart found for account: " + accountDto.getEmail() + ", creating a new cart.");
            return cartDAO.createCartFor(accountDto).toDTO();
        }
        logger.info(cart.toString());
        CartDTO cartDto = cart.toDTO();
        logger.info(cartDto.toString());
        logger.info("Returning " + cartDto);
        return cartDto;
    }

    public void addBookToCart(AccountDTO accountDto, BookDTO bookDto) throws UnsufficientStockException, InvalidCartException {
        try {
            accountDto = accountAPI.signin(accountDto.getEmail(), accountDto.getPassword());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        CartDTO cart = this.getCart(accountDto);
        if (!bookAPI.isInStock(bookDto)) {
            logger.warning("Book is not in stock: " + bookDto.getTitle());
            throw new UnsufficientStockException("Book is not in stock: " + bookDto.getTitle());
        } else if (cart.getBookIsbn().containsKey(bookDto.getIsbn()) && !bookAPI.isSufficientlyInStock(bookDto, cart.getBookIsbn().get(bookDto.getIsbn()))) {
            logger.warning("Book " + bookDto.getTitle() + "is not in sufficient stock: " + bookDto.getTitle());
            throw new UnsufficientStockException("Book is not in sufficient stock: " + bookDto.getTitle());
        }
        logger.info("Adding book to cart for account: " + accountDto.getEmail() + ", Book: " + bookDto.getTitle());
        cartDAO.addBookToCart(accountDto, bookDto);
    }

    public void removeBookFromCart(AccountDTO accountDto, BookDTO bookDto) throws InvalidCartException {
        try {
            accountDto = accountAPI.signin(accountDto.getEmail(), accountDto.getPassword());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cartDAO.removeBookFromCart(accountDto.toAccount(), bookDto);
    }

    public void clearCart(AccountDTO accountDto) throws InvalidCartException {
        try {
            accountDto = accountAPI.signin(accountDto.getEmail(), accountDto.getPassword());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        cartDAO.clearCart(accountDto.toAccount());
    }

}
