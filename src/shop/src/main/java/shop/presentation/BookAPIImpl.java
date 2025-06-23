package shop.presentation;

import java.util.List;
import java.util.Map;

import shop.business.BookService;
import shop.dto.BookDTO;
import shop.model.Book;
import shop.model.BookProperty;

public class BookAPIImpl implements BookAPI {
    private final BookService bookService;

    public BookAPIImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public List<BookDTO> getBooksBy(Map<BookProperty, String> criteria) throws Exception {
        List<BookDTO> books = this.bookService.getBooksBy(criteria);

        return books;
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
    public BookDTO createBook(BookDTO bookDTO) throws Exception {
        BookDTO book = this.bookService.createBook(bookDTO);
        if (book == null) {
            throw new Exception("Book creation failed");
        }

        return book;
    }

    @Override
    public void deleteBook(Book book) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBook'");
    }
}
