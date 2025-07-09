package ca.uqam.mgl7361.lel.gp1.lel.gp1.account.apis;

import java.util.List;
import java.util.Map;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.dtos.shop.BookDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.dtos.shop.BookProperty;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.interfaces.BookAPI;

public class BookAPIImpl implements BookAPI {

    @Override
    public List<BookDTO> getBooksBy(Map<BookProperty, String> criteria) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBooksBy'");
    }

    @Override
    public void setPropertiesFor(BookDTO bookDTO, Map<BookProperty, List<String>> properties) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPropertiesFor'");
    }

    @Override
    public void removePropertiesFrom(BookDTO bookDTO, Map<BookProperty, List<String>> properties) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removePropertiesFrom'");
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createBook'");
    }

    @Override
    public void deleteBook(BookDTO bookDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBook'");
    }

    @Override
    public void addBook(BookDTO bookDTO) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addBook'");
    }

    @Override
    public boolean isInStock(BookDTO bookDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isInStock'");
    }

    @Override
    public boolean isSufficientlyInStock(BookDTO bookDto, int quantity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isSufficientlyInStock'");
    }

    
}
