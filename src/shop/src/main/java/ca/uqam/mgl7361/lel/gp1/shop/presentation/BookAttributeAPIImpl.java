package ca.uqam.mgl7361.lel.gp1.shop.presentation;

import ca.uqam.mgl7361.lel.gp1.shop.dto.BookAttributeDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.AuthorDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.CategoryDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.PublisherDTO;
import ca.uqam.mgl7361.lel.gp1.shop.business.BookAttributeService;
import ca.uqam.mgl7361.lel.gp1.shop.exception.DTOException;

import java.util.List;

public class BookAttributeAPIImpl implements BookAttributeAPI {

    private final BookAttributeService bookAttributeService;

    public BookAttributeAPIImpl() {
        this.bookAttributeService = new BookAttributeService();
    }

    @Override
    public List<AuthorDTO> getAuthors() throws DTOException {
        return bookAttributeService.getAuthors();
    }

    @Override
    public List<CategoryDTO> getCategories() throws DTOException {
        return bookAttributeService.getCategories();
    }

    @Override
    public List<PublisherDTO> getPublishers() throws DTOException {
        return bookAttributeService.getPublishers();
    }

    @Override
    public void addAttributes(List<BookAttributeDTO> bookAttributeDTO) throws DTOException {
        bookAttributeService.addAttributes(bookAttributeDTO);
    }

    @Override
    public void removeAttribute(BookAttributeDTO bookAttributeDTO) throws DTOException {
        bookAttributeService.removeAttribute(bookAttributeDTO);
    }
}
