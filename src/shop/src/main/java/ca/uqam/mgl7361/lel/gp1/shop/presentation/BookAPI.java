package ca.uqam.mgl7361.lel.gp1.shop.presentation;

import java.util.List;
import java.util.Map;

import ca.uqam.mgl7361.lel.gp1.shop.dto.BookProperty;
import ca.uqam.mgl7361.lel.gp1.shop.exception.DTOException;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookDTO;

public interface BookAPI {
    List<BookDTO> getBooksBy(Map<BookProperty, String> criteria) throws Exception;
    void setPropertiesFor(BookDTO bookDTO, Map<BookProperty, List<String>> properties);
    void removePropertiesFrom(BookDTO bookDTO, Map<BookProperty, List<String>> properties);
    BookDTO createBook(BookDTO bookDTO) throws Exception;
    void deleteBook(BookDTO bookDTO) throws DTOException, Exception;
    void addBook(BookDTO bookDTO) throws Exception;
    boolean isInStock(BookDTO bookDto);
    boolean isSufficientlyInStock(BookDTO bookDto, int quantity);
}