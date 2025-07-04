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

    public CartAPIImpl() {
    }

    public CartDTO getCart(AccountDTO accountDto) {
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
    public void clearCart(AccountDTO accountDto) {
        cartService.clearCart(accountDto);
        System.out.println("Panier vidé avec succès !");
    }
}
