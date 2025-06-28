package shop.presentation;

import java.util.List;
import java.util.Map;

import shop.model.Book;
import shop.dto.BookProperty;
import shop.dto.BookDTO;

public interface BookAPI {
    List<BookDTO> getBooksBy(Map<BookProperty, String> criteria) throws Exception;
    void setPropertiesFor(Book book, Map<BookProperty, List<String>> properties);
    void removePropertiesFrom(Book book, Map<BookProperty, List<String>> properties);
    BookDTO createBook(BookDTO bookDTO) throws Exception;
    void deleteBook(Book book);
    void addBook(Book book) throws Exception;
}