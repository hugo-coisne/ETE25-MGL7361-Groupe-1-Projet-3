package shop.business;

import shop.model.BookAttribute;
import shop.model.Author;
import shop.model.Category;
import shop.model.Publisher;
import shop.persistence.BookAttributeDAO;
import shop.exception.DTOException;

import java.util.List;

public class BookAttributeService {

    private final BookAttributeDAO bookAttributeDAO = new BookAttributeDAO();

    public List<Author> getAuthors() throws DTOException {
        try {
            return bookAttributeDAO.getAuthors();
        } catch (Exception e) {
            throw new DTOException("Unable to retrieve authors");
        }
    }

    public List<Category> getCategories() throws DTOException {
        try {
            return bookAttributeDAO.getCategories();
        } catch (Exception e) {
            throw new DTOException("Unable to retrieve categories");
        }
    }

    public List<Publisher> getPublishers() throws DTOException {
        try {
            return bookAttributeDAO.getPublishers();
        } catch (Exception e) {
            throw new DTOException("Unable to retrieve publishers");
        }
    }

    public void addAttributes(List<BookAttribute> bookAttributes) throws DTOException {
        try {
            bookAttributeDAO.addAttributes(bookAttributes);
        } catch (Exception e) {
            throw new DTOException("Unable to add attributes");
        }
    }

    public void removeAttribute(BookAttribute bookAttribute) throws DTOException {
        try {
            bookAttributeDAO.removeAttribute(bookAttribute);
        } catch (Exception e) {
            throw new DTOException("Unable to remove attribute");
        }
    }
}
