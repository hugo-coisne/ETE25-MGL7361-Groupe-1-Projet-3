package ca.uqam.mgl7361.lel.gp1.shop.business;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import ca.uqam.mgl7361.lel.gp1.shop.exception.InvalidCartException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.UnsufficientStockException;
import ca.uqam.mgl7361.lel.gp1.common.clients.AccountAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.BookAPIClient;
import ca.uqam.mgl7361.lel.gp1.common.clients.Clients;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookStockQuantityRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;
import ca.uqam.mgl7361.lel.gp1.shop.business.mapper.CartMapper;
import ca.uqam.mgl7361.lel.gp1.shop.model.Cart;
import ca.uqam.mgl7361.lel.gp1.shop.persistence.CartDAO;

@Service
public class CartService {

    static CartService instance;
    Logger logger = LogManager.getLogger(CartService.class.getName());

    AccountAPIClient accountAPIClient = Clients.accountClient;
    BookAPIClient bookAPIClient = Clients.bookClient;

    CartDAO cartDAO = new CartDAO();

    public static CartService getInstance() {
        if (instance == null) {
            instance = new CartService();
        }
        return instance;
    }

    public CartDTO getCart(AccountDTO accountDto) {
        logger.debug("Retrieving cart for account: " + accountDto.getEmail());
        Map<String, String> credentialsMap = Map.of("email", accountDto.getEmail(), "password",
                accountDto.getPassword());
        accountDto = accountAPIClient.signin(credentialsMap);

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
            throws UnsufficientStockException, InvalidCartException {
        Map<String, String> credentialsMap = Map.of("email", accountDto.getEmail(), "password",
                accountDto.getPassword());
        accountDto = accountAPIClient.signin(credentialsMap);

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
            throws InvalidCartException {
        Map<String, String> credentialsMap = Map.of("email", accountDto.getEmail(), "password",
                accountDto.getPassword());
        accountDto = accountAPIClient.signin(credentialsMap);
        cartDAO.removeBookFromCart(accountDto.getId(), bookDto);
    }

    public void clearCart(AccountDTO accountDto) throws InvalidCartException {
        Map<String, String> credentialsMap = Map.of("email", accountDto.getEmail(), "password",
                accountDto.getPassword());
        accountDto = accountAPIClient.signin(credentialsMap);
        try {
            cartDAO.clearCart(accountDto.getId());
        } catch (Exception e) {
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
