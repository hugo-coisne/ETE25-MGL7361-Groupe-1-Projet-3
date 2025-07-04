package account.presentation;

import java.util.List;
import java.util.Map;

import account.dto.AccountDTO;
import account.dto.CartDTO;
import shop.dto.BookDTO;

public interface CartAPI {

    public CartDTO getCart(AccountDTO accountDto);

    public void add(BookDTO bookDto, AccountDTO accountDto);

    public void add(BookDTO bookDto, int quantity, AccountDTO accountDto);

    public void add(List<BookDTO> bookDtos, AccountDTO accountDto);

    public void add(Map<BookDTO, Integer> bookDtoQuantities, AccountDTO accountDto);

    public void remove(BookDTO bookDto, AccountDTO accountDto);

    public void remove(BookDTO bookDto, int quantity, AccountDTO accountDto);

    public void remove(List<BookDTO> bookDtos, AccountDTO accountDto);

    public void remove(Map<BookDTO, Integer> bookDtoQuantities, AccountDTO accountDto);

    public void clearCart(AccountDTO accountDto);

}
