package ca.uqam.mgl7361.lel.gp1.shop.business;

import ca.uqam.mgl7361.lel.gp1.shop.dto.BookDTO;
import ca.uqam.mgl7361.lel.gp1.shop.business.mapper.BookMapper;
import ca.uqam.mgl7361.lel.gp1.shop.exception.DTOException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.DuplicationBookException;
import ca.uqam.mgl7361.lel.gp1.shop.model.Book;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookProperty;
import ca.uqam.mgl7361.lel.gp1.shop.persistence.BookDAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    public BookDTO createBook(BookDTO bookDTO) throws DuplicationBookException, Exception {
        Book book = BookMapper.toModel(bookDTO);
        bookDAO.save(book);

        return BookMapper.toDTO(book);
    }

    public List<BookDTO> getBooksBy(Map<BookProperty, String> criteria) throws Exception {
        // TODO : Add checking for criteria validity
        // TODO : Add the ability to retrieve publisher, categories and authors
        // information, and add them to the Book then the BookDTO
        List<Book> books = this.bookDAO.getBooksBy(criteria);

        return books.stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void setPropertiesFor(BookDTO bookDto, Map<BookProperty, List<String>> properties) throws DTOException {
        Book book = BookMapper.toModel(bookDto);
        bookDAO.setPropertiesFor(book, properties);
    }

    public void addBook(BookDTO bookDto) throws DuplicationBookException, Exception {
        Book book = BookMapper.toModel(bookDto);
        bookDAO.save(book);
    }

    public void deleteBook(BookDTO bookDto) throws DTOException, Exception {
        Book book = BookMapper.toModel(bookDto);
        bookDAO.deleteBook(book);
    }

    public void removePropertiesFrom(BookDTO bookDto, Map<BookProperty, List<String>> properties) throws DTOException {
        Book book = BookMapper.toModel(bookDto);
        bookDAO.removePropertiesFrom(book, properties);
    }

    public boolean isInStock(BookDTO bookDto) throws DTOException {
        Book book = BookMapper.toModel(bookDto);
        return bookDAO.isInStock(book);
    }

    public boolean isSufficientlyInStock(BookDTO bookDto, int quantity) throws DTOException {
        Book book = BookMapper.toModel(bookDto);
        return bookDAO.isSufficientlyInStock(book, quantity);
    }

    public void decreasedBookStockQuantity(String isbn, int quantity) throws Exception {
        Book book = bookDAO.getBooksBy(Map.of(BookProperty.ISBN, isbn)).getFirst();
        bookDAO.update(isbn, book.getStockQuantity() - quantity);
    }

}
