package shop.presentation;

import java.util.List;
import java.util.Map;

import shop.model.Book;
import shop.dto.BookProperty;
import shop.dto.BookDTO;

public interface BookAPI {
    public List<BookDTO> getBooksBy(Map<BookProperty, String> criteria) throws Exception;

    public void setPropertiesFor(Book book, Map<BookProperty, List<String>> properties);

    public void removePropertiesFrom(Book book, Map<BookProperty, List<String>> properties);

    public BookDTO createBook(BookDTO bookDTO) throws Exception;

    public void deleteBook(Book book);

}