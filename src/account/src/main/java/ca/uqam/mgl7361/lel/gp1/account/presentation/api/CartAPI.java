package ca.uqam.mgl7361.lel.gp1.account.presentation.api;

import ca.uqam.mgl7361.lel.gp1.account.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.account.dto.CartDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.dtos.shop.BookDTO;

public interface CartAPI {

    public CartDTO getCart(AccountDTO accountDto);

    public void add(BookDTO bookDto, AccountDTO accountDto);

    public void remove(BookDTO bookDto, AccountDTO accountDto);

    public void clearCart(AccountDTO accountDto);

}
