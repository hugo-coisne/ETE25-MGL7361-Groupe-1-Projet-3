package shop.business;

import shop.dto.BookDTO;
import shop.business.mapper.BookMapper;
import shop.exception.DTOException;
import shop.exception.DuplicationBookException;
import shop.model.Book;
import shop.dto.BookProperty;
import shop.persistence.BookDAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.sql.*;


public class BookService {
    private final BookDAO bookDAO;


    public BookService() {
        this.bookDAO = new BookDAO();
    }

    public BookDTO createBook(BookDTO bookDTO) throws DTOException, DuplicationBookException {
        Book book = BookMapper.toModel(bookDTO);
        try {
            bookDAO.save(book);
            return BookMapper.toDTO(book);
        } catch (DuplicationBookException e) {
            throw e;
        } catch (Exception e) {
            throw new DTOException("Failed to create book: " + e.getMessage());
        }
    }

    public List<BookDTO> getBooksBy(Map<BookProperty, String> criteria) throws Exception {
        // TODO : Add checking for criteria validity
        // TODO : Add the ability to retrieve publisher, categories and authors information, and add them to the Book then the BookDTO
        List<Book> books = this.bookDAO.getBooksBy(criteria);

        // Convert the list of Book objects to BookDTO objects
        return books.stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void setPropertiesFor(Book book, Map<BookProperty, List<String>> properties) throws DTOException {
        try {
            bookDAO.setPropertiesFor(book, properties);
        } catch (Exception e) {
            throw new DTOException("Failed to set properties for book: " + book.getIsbn());
        }
    }

    public void addBook(Book book) throws DTOException {
        try {
            // Ajout du livre via le DAO
            bookDAO.save(book);

        } catch (SQLException e) {
            throw new DTOException("Erreur SQL lors de l'ajout du livre : " + book.getIsbn());
        } catch (Exception e) {
            throw new DTOException("Erreur inattendue lors de l'ajout du livre : " + book.getIsbn());
        }
    }

    public void deleteBook(Book book) throws DTOException {
        try {
            bookDAO.deleteBook(book);
        } catch (Exception e) {
            throw new DTOException("Failed to delete book: " + book.getIsbn());
        }
    }

    public void removePropertiesFrom(Book book, Map<BookProperty, List<String>> properties) throws DTOException {
        try {
            bookDAO.removePropertiesFrom(book, properties);
        } catch (Exception e) {
            throw new DTOException("Failed to remove properties from book: " + book.getIsbn());
        }
    }

}
