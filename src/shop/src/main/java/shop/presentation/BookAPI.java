package shop.presentation;

import java.util.List;
import java.util.Map;

import shop.business.entities.Book;
import shop.business.entities.BookProperty;

public interface BookAPI {
    public List<Book> getBooksBy(Map<BookProperty, String> criteria);

    public Book createBook(String title, String description, String isbn);

    public void setPropertiesFor(Book book, Map<BookProperty, List<String>> properties);
    public void removePropertiesFrom(Book book, Map<BookProperty, List<String>> properties);
            
    public void addBook(Book book);
    public void deleteBook(Book book);
    
}