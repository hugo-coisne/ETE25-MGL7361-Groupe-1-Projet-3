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

    public List<BookDTO> getBooksBy(Map<BookProperty, String> criteria) throws DTOException {
        // TODO : Add checking for criteria validity
        // TODO : Add the ability to retrieve publisher, categories and authors information, and add them to the Book then the BookDTO
        try {
            List<Book> books = this.bookDAO.getBooksBy(criteria);

            return books.stream()
                    .map(BookMapper::toDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new DTOException("Failed to retrieve books by criteria");
        }
    }

    public void setPropertiesFor(BookDTO bookDto, Map<BookProperty, List<String>> properties) throws DTOException {
        try {
            Book book = BookMapper.toModel(bookDto);
            bookDAO.setPropertiesFor(book, properties);
            } catch (Exception e) {
            throw new DTOException("Failed to set properties for book: " + bookDto.getIsbn());
        }
    }

    public void addBook(BookDTO bookDto) throws DTOException {
        try {
            Book book = BookMapper.toModel(bookDto);
            bookDAO.save(book);
        } catch (SQLException e) {
            throw new DTOException("Erreur SQL lors de l'ajout du livre : " + bookDto.getIsbn());
        } catch (Exception e) {
            throw new DTOException("Erreur inattendue lors de l'ajout du livre : " + bookDto.getIsbn());
        }
    }

    public void deleteBook(BookDTO bookDto) throws DTOException {
        try {
            Book book = BookMapper.toModel(bookDto);
            bookDAO.deleteBook(book);
        } catch (Exception e) {
            throw new DTOException("Failed to delete book: " + bookDto.getIsbn());
        }
    }

    public void removePropertiesFrom(BookDTO bookDto, Map<BookProperty, List<String>> properties) throws DTOException {
        try {
            Book book = BookMapper.toModel(bookDto);
            bookDAO.removePropertiesFrom(book, properties);
        } catch (Exception e) {
            throw new DTOException("Failed to remove properties from book: " + bookDto.getIsbn());
        }
    }

    public boolean isInStock(BookDTO bookDto) throws DTOException {
        try {
            Book book = BookMapper.toModel(bookDto);
            return bookDAO.isInStock(book);
        } catch (SQLException e) {
            throw new DTOException("Failed to check stock for book: " + bookDto.getIsbn());
        }
    }

    public boolean isSufficientlyInStock(BookDTO bookDto, int quantity) throws DTOException {
        try {
            Book book = BookMapper.toModel(bookDto);
            return bookDAO.isSufficientlyInStock(book, quantity);
        } catch (SQLException e) {
            throw new DTOException("Failed to check stock quantity for book: " + bookDto.getIsbn());
        }
    }

}
