package shop.presentation;

import java.util.List;
import java.util.Map;

import shop.business.entities.Book;
import shop.business.entities.BookProperty;

public class BookAPIImpl implements BookAPI {

    @Override
    public List<Book> getBooksBy(Map<BookProperty, String> criteria) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBooksBy'");
    }

    @Override
    public Book createBook(String title, String description, String isbn) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createBook'");
    }

    @Override
    public void setPropertiesFor(Book book, Map<BookProperty, List<String>> properties) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPropertiesFor'");
    }

    @Override
    public void removePropertiesFrom(Book book, Map<BookProperty, List<String>> properties) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removePropertiesFrom'");
    }

    @Override
    public void addBook(Book book) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addBook'");
    }

    @Override
    public void deleteBook(Book book) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBook'");
    }
    
}
