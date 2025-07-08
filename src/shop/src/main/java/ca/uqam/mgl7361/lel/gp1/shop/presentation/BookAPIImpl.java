package ca.uqam.mgl7361.lel.gp1.shop.presentation;

import java.util.List;
import java.util.Map;

import ca.uqam.mgl7361.lel.gp1.shop.business.BookService;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookProperty;

public class BookAPIImpl implements BookAPI {
    private final BookService bookService;

    public BookAPIImpl() {
        this.bookService = new BookService();
    }

    @Override
    public List<BookDTO> getBooksBy(Map<BookProperty, String> criteria) {

        return this.bookService.getBooksBy(criteria);
    }

    @Override
    public void setPropertiesFor(BookDTO bookDTO, Map<BookProperty, List<String>> properties) {
        bookService.setPropertiesFor(bookDTO, properties);
    }

    @Override
    public void removePropertiesFrom(BookDTO bookDTO, Map<BookProperty, List<String>> properties) {
        bookService.removePropertiesFrom(bookDTO, properties);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        return bookService.createBook(bookDTO);
    }


    @Override
    public void deleteBook(BookDTO bookDTO) {
        bookService.deleteBook(bookDTO);
    }

    @Override
    public void addBook(BookDTO bookDTO) {
        bookService.addBook(bookDTO);
    }

    @Override
    public boolean isInStock(BookDTO bookDto) {
        return bookService.isInStock(bookDto);
    }

    @Override
    public boolean isSufficientlyInStock(BookDTO bookDto, int quantity) {
        return bookService.isSufficientlyInStock(bookDto, quantity);
    }
}
