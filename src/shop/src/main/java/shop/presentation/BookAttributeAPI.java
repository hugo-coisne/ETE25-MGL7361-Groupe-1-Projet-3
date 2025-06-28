package shop.presentation;

import shop.model.Author;
import shop.model.Category;
import shop.model.Publisher;
import shop.model.BookAttribute;
import shop.exception.DTOException;

import java.util.List;

public interface BookAttributeAPI {
    List<Author> getAuthors() throws DTOException;
    List<Category> getCategories() throws DTOException;
    List<Publisher> getPublishers() throws DTOException;

    void addAttributes(List<BookAttribute> bookAttributes) throws DTOException;
    void removeAttribute(BookAttribute bookAttribute) throws DTOException;
}
