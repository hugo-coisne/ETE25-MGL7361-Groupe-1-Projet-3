package ca.uqam.mgl7361.lel.gp1.common.clients;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartBookRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;
import feign.Headers;
import feign.Param;
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

    @RequestLine("GET /cart/view/{accountId}")
    @Headers("Accept: application/json")
    CartDTO getCart(@Param("accountId") int accountId);

    @RequestLine("DELETE /cart/clear/{id}")
    @Headers("Content-Type: application/json")
    void clearCart(@Param("id") int id);
}
