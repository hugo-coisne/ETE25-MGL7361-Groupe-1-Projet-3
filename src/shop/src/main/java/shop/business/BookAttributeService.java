package shop.business;

import shop.business.mapper.BookAttributeMapper;
import shop.dto.AuthorDTO;
import shop.dto.BookAttributeDTO;
import shop.dto.CategoryDTO;
import shop.dto.PublisherDTO;
import shop.model.BookAttribute;
import shop.model.Author;
import shop.model.Category;
import shop.model.Publisher;
import shop.persistence.BookAttributeDAO;
import shop.exception.DTOException;

import java.util.List;
import java.util.stream.Collectors;

public class BookAttributeService {

    private final BookAttributeDAO bookAttributeDAO = new BookAttributeDAO();

    public List<AuthorDTO> getAuthors() throws DTOException {
        try {
            List<Author> authors = bookAttributeDAO.getAuthors();
            return authors.stream()
                    .map(author -> new AuthorDTO(author.getId(), author.getName()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DTOException("Unable to retrieve authors");
        }
    }

    public List<CategoryDTO> getCategories() throws DTOException {
        try {
            List<Category> categories = bookAttributeDAO.getCategories();
            return categories.stream()
                    .map(category -> new CategoryDTO(category.getId(), category.getName()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DTOException("Unable to retrieve categories");
        }
    }

    public List<PublisherDTO> getPublishers() throws DTOException {
        try {
            List<Publisher> publishers = bookAttributeDAO.getPublishers();
            return publishers.stream()
                    .map(publisher -> new PublisherDTO(publisher.getId(), publisher.getName()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DTOException("Unable to retrieve publishers");
        }
    }

    public void addAttributes(List<BookAttributeDTO> bookAttributeDTOs) throws DTOException {
        try {
            List<BookAttribute> attributes = bookAttributeDTOs.stream()
                    .map(BookAttributeMapper::toModel)
                    .toList();

            bookAttributeDAO.addAttributes(attributes);
        } catch (Exception e) {
            throw new DTOException("Unable to add attributes");
        }
    }


    public void removeAttribute(BookAttributeDTO bookAttributeDTO) throws DTOException {
        try {
            BookAttribute bookAttribute = BookAttributeMapper.toModel(bookAttributeDTO);
            bookAttributeDAO.removeAttribute(bookAttribute);
        } catch (Exception e) {
            throw new DTOException("Unable to remove attribute");
        }
    }
}
