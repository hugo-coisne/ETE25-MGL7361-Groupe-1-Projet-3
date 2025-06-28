package shop.presentation;

import java.util.List;
import java.util.Map;

import shop.business.BookService;
import shop.dto.BookDTO;
import shop.model.Book;
import shop.dto.BookProperty;

public class BookAPIImpl implements BookAPI {
    private final BookService bookService;

    public BookAPIImpl() {
        this.bookService = new BookService();
    }

    @Override
    public List<BookDTO> getBooksBy(Map<BookProperty, String> criteria) throws Exception {

        return this.bookService.getBooksBy(criteria);
    }

    @Override
    public void setPropertiesFor(Book book, Map<BookProperty, List<String>> properties) {
        bookService.setPropertiesFor(book, properties);
    }

    @Override
    public void removePropertiesFrom(Book book, Map<BookProperty, List<String>> properties) {
        bookService.removePropertiesFrom(book, properties);
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
        bookService.deleteBook(book);
    }

    @Override
    public void addBook(Book book) {
        bookService.addBook(book);
    }
}
