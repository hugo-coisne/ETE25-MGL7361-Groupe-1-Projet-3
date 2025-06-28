package shop.presentation;

import shop.model.Author;
import shop.model.Category;
import shop.model.Publisher;
import shop.model.BookAttribute;
import shop.business.BookAttributeService;
import shop.exception.DTOException;

import java.util.List;

public class BookAttributeAPIImpl implements BookAttributeAPI {

    private final BookAttributeService bookAttributeService;

    public BookAttributeAPIImpl() {
        this.bookAttributeService = new BookAttributeService();
    }

    @Override
    public List<Author> getAuthors() throws DTOException {
        return bookAttributeService.getAuthors();
    }

    @Override
    public List<Category> getCategories() throws DTOException {
        return bookAttributeService.getCategories();
    }

    @Override
    public List<Publisher> getPublishers() throws DTOException {
        return bookAttributeService.getPublishers();
    }

    @Override
    public void addAttributes(List<BookAttribute> bookAttributes) throws DTOException {
        bookAttributeService.addAttributes(bookAttributes);
    }

    @Override
    public void removeAttribute(BookAttribute bookAttribute) throws DTOException {
        bookAttributeService.removeAttribute(bookAttribute);
    }
}
