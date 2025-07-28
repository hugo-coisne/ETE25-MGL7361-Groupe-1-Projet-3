package ca.uqam.mgl7361.lel.gp1.common.interfaces;

import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.CartDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;

public interface CartAPI {

    public CartDTO getCart(AccountDTO accountDto);

    public void add(BookDTO bookDto, AccountDTO accountDto);

    public void remove(BookDTO bookDto, AccountDTO accountDto);

    public void clearCart(AccountDTO accountDto);

}
