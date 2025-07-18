package ca.uqam.mgl7361.lel.gp1.common.interfaces;

import ca.uqam.mgl7361.lel.gp1.common.dtos.account.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.account.CartDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;

public interface CartAPI {

    public CartDTO getCart(AccountDTO accountDto);

    public void add(BookDTO bookDto, AccountDTO accountDto);

    public void remove(BookDTO bookDto, AccountDTO accountDto);

    public void clearCart(AccountDTO accountDto);

}
