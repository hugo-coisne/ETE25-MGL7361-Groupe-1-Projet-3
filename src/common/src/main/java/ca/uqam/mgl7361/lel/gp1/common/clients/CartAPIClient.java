package ca.uqam.mgl7361.lel.gp1.common.clients;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;
import feign.Headers;
import feign.RequestLine;

public interface CartAPIClient {

    @RequestLine("POST /cart/view")
    @Headers("Content-Type: application/json")
    CartDTO getCart(AccountDTO account);

    @RequestLine("POST /cart/add")
    @Headers("Content-Type: application/json")
    void addBookToCart(CartBookRequest request);

    @RequestLine("POST /cart/remove")
    @Headers("Content-Type: application/json")
    void removeBookFromCart(CartBookRequest request);

    @RequestLine("POST /cart/clear")
    @Headers("Content-Type: application/json")
    void clearCart(AccountDTO account);

    record CartBookRequest(AccountDTO account, BookDTO book) {
    }
}
