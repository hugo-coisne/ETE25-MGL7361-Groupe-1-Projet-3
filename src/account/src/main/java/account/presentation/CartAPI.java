package account.presentation;

import java.util.List;
import java.util.Map;

import account.dto.AccountDTO;
import account.dto.CartDTO;
import shop.dto.BookDTO;

public interface CartAPI {

    public CartDTO getCart(AccountDTO accountDto);

    public void add(BookDTO bookDto);

    public void add(BookDTO bookDto, int quantity);

    public void add(List<BookDTO> bookDtos);

    public void add(Map<BookDTO, Integer> bookDtoQuantities);

    public void remove(BookDTO bookDto);

    public void remove(BookDTO bookDto, int quantity);

    public void remove(List<BookDTO> bookDtos);

    public void remove(Map<BookDTO, Integer> bookDtoQuantities);

    public void clearCart(AccountDTO accountDto);

}
