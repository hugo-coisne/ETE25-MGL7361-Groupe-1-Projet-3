package shop.presentation;

import shop.dto.BookAttributeDTO;
import shop.dto.AuthorDTO;
import shop.dto.CategoryDTO;
import shop.dto.PublisherDTO;
import shop.business.BookAttributeService;
import shop.exception.DTOException;

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
