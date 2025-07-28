package ca.uqam.mgl7361.lel.gp1.user.business;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidCartException;
import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidCredentialsException;
import ca.uqam.mgl7361.lel.gp1.user.exception.UnsufficientStockException;
import ca.uqam.mgl7361.lel.gp1.user.mapper.AccountMapper;
import ca.uqam.mgl7361.lel.gp1.user.mapper.CartMapper;
import ca.uqam.mgl7361.lel.gp1.user.model.Cart;
import ca.uqam.mgl7361.lel.gp1.user.persistence.CartDAO;
import ca.uqam.mgl7361.lel.gp1.common.clients.BookAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.Clients;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookStockQuantityRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;

@Service
public class CartService {

    static CartService instance;
    Logger logger = LogManager.getLogger(CartService.class.getName());

    static AccountService accountService = AccountService.getInstance();
    BookAPIClient bookAPIClient = Clients.bookClient;

    CartDAO cartDAO = new CartDAO();

    public static CartService getInstance() {
        if (instance == null) {
            instance = new CartService();
        }
        return instance;
    }

    public CartDTO getCart(AccountDTO accountDto) throws InvalidCredentialsException {
        logger.debug("Retrieving cart for account: " + accountDto.getEmail());
        accountDto = accountService.signin(accountDto.getEmail(), accountDto.getPassword());

        Cart cart = cartDAO.getCart(accountDto);

        if (cart.getId() < 0 || cart.getTotalPrice() < 0) {
            logger.debug("No cart found for account: " + accountDto.getEmail() + ", creating a new cart.");
            return CartMapper.toDTO(cartDAO.createCartFor(accountDto));
        }
        logger.debug(cart.toString());
        CartDTO cartDto = CartMapper.toDTO(cart);
        logger.debug(cartDto.toString());
        logger.debug("Returning " + cartDto);
        return cartDto;
    }

    public void addBookToCart(AccountDTO accountDto, BookDTO bookDto)
            throws UnsufficientStockException, InvalidCartException, InvalidCredentialsException {
        accountDto = accountService.signin(accountDto.getEmail(), accountDto.getPassword());

        CartDTO cart = this.getCart(accountDto);
        logger.debug("Calling bookAPIClient to check if " + bookDto + " is in stock then sufficiently in stock");
        if (!bookAPIClient.isInStock(bookDto)) {
            logger.error("Book is not in stock: " + bookDto.getTitle());
            throw new UnsufficientStockException("Book is not in stock: " + bookDto.getTitle());
        } else if (cart.getBookIsbn().containsKey(bookDto.getIsbn())
                && !bookAPIClient.isSufficientlyInStock(
                        new BookStockQuantityRequest(bookDto, cart.getBookIsbn().get(bookDto.getIsbn())))) {
            logger.error("Book " + bookDto.getTitle() + "is not in sufficient stock: " + bookDto.getTitle());
            throw new UnsufficientStockException("Book is not in sufficient stock: " + bookDto.getTitle());
        }
        logger.debug("Adding book to cart for account: " + accountDto.getEmail() + ", Book: " + bookDto.getTitle());
        cartDAO.addBookToCart(accountDto, bookDto);
    }

    public void removeBookFromCart(AccountDTO accountDto, BookDTO bookDto)
            throws InvalidCartException, InvalidCredentialsException {
        accountDto = accountService.signin(accountDto.getEmail(), accountDto.getPassword());
        cartDAO.removeBookFromCart(AccountMapper.toModel(accountDto), bookDto);
    }

    public void clearCart(AccountDTO accountDto) throws InvalidCartException, InvalidCredentialsException {
        accountDto = accountService.signin(accountDto.getEmail(), accountDto.getPassword());
        try {
            cartDAO.clearCart(AccountMapper.toModel(accountDto));
        } catch (InvalidCartException e) {
            logger.debug("No cart found for account id, considered clear");
        }
    }

    public CartDTO getCartFor(int accountId) {
        logger.debug("Retrieving cart for account id : " + accountId);

        Cart cart = cartDAO.getCartFor(accountId);

        if (cart.getId() < 0 || cart.getTotalPrice() < 0) {
            logger.debug("No cart found for account id : " + accountId);
            return null;
        }
        logger.debug(cart.toString());
        CartDTO cartDto = CartMapper.toDTO(cart);
        logger.debug(cartDto.toString());
        logger.debug("Returning " + cartDto);
        return cartDto;
    }

    public void clearCart(int id) {
        cartDAO.clearCart(id);
        logger.debug("Cart with id " + id + " cleared.");
    }

}
