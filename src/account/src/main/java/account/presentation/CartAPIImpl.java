package account.presentation;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import account.business.CartService;
import account.dto.AccountDTO;
import account.dto.CartDTO;
import shop.dto.BookDTO;

public class CartAPIImpl implements CartAPI {

    private AccountDTO accountDto;

    private static CartService cartService = CartService.getInstance();

    static Logger logger = Logger.getLogger(CartAPIImpl.class.getName());

    private CartAPIImpl(AccountDTO accountDto) {
        this.accountDto = accountDto;
    }

    public static CartAPI getCartAPI(AccountDTO accountDto) {
        logger.info("Creating and returning CartAPI for account: " + accountDto.getEmail());
        return new CartAPIImpl(accountDto);
    }

    public CartDTO getCart() {
        logger.info("Retrieving cart for account: " + accountDto.getEmail());
        CartDTO cartDto = cartService.getCart(accountDto);
        logger.info("Cart retrieved for account: " + accountDto.getEmail() + ", Cart ID: " + cartDto.getId()
                + ", Total Price: " + cartDto.getTotalPrice());
        return cartDto;
    }

    @Override
    public void add(BookDTO bookDto) {
        cartService.addBookToCart(accountDto, bookDto);
    }

    @Override
    public void add(List<BookDTO> bookDtos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void add(BookDTO bookDto, int quantity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void add(Map<BookDTO, Integer> bookDtoQuantities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void remove(BookDTO bookDto) {
        cartService.removeBookFromCart(accountDto, bookDto);
    }

    @Override
    public void remove(BookDTO bookDto, int quantity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void remove(List<BookDTO> bookDtos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void remove(Map<BookDTO, Integer> bookDtoQuantities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public void clear() {
        cartService.clearCart(accountDto);
    }

    @Override
    public double getTotalPrice() {
        return cartService.getCartTotalPrice(accountDto);
    }

}
