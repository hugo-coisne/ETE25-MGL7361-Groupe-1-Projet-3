package ca.uqam.mgl7361.lel.gp1.lel.gp1.common.interfaces;

import java.util.List;
import java.util.Map;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.dtos.shop.BookDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.dtos.shop.BookProperty;

public interface BookAPI {
    List<BookDTO> getBooksBy(Map<BookProperty, String> criteria) throws Exception;
    void setPropertiesFor(BookDTO bookDTO, Map<BookProperty, List<String>> properties);
    void removePropertiesFrom(BookDTO bookDTO, Map<BookProperty, List<String>> properties);
    BookDTO createBook(BookDTO bookDTO) throws Exception;
    void deleteBook(BookDTO bookDTO);
    void addBook(BookDTO bookDTO) throws Exception;
    boolean isInStock(BookDTO bookDto);
    boolean isSufficientlyInStock(BookDTO bookDto, int quantity);
}