package ca.uqam.mgl7361.lel.gp1.common.clients;

import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookProperty;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;
import java.util.Map;

public interface BookAPIClient {

    @RequestLine("POST /books/search")
    @Headers("Content-Type: application/json")
    List<BookDTO> getBooksBy(Map<BookProperty, String> criteria);

    @RequestLine("POST /books")
    @Headers("Content-Type: application/json")
    BookDTO createBook(BookDTO book);

    @RequestLine("POST /books/add")
    @Headers("Content-Type: application/json")
    void addBook(BookDTO book);

    @RequestLine("DELETE /books")
    @Headers("Content-Type: application/json")
    void deleteBook(BookDTO book);

    @RequestLine("POST /books/properties/set")
    @Headers("Content-Type: application/json")
    void setPropertiesFor(BookPropertiesRequest request);

    @RequestLine("POST /books/properties/remove")
    @Headers("Content-Type: application/json")
    void removePropertiesFrom(BookPropertiesRequest request);

    @RequestLine("POST /books/in-stock")
    @Headers("Content-Type: application/json")
    Boolean isInStock(BookDTO book);

    @RequestLine("POST /books/in-stock/{quantity}")
    @Headers("Content-Type: application/json")
    Boolean isSufficientlyInStock(BookDTO book, @Param("quantity") int quantity);

    record BookPropertiesRequest(
            BookDTO book,
            Map<BookProperty, List<String>> properties) {
    }
}
