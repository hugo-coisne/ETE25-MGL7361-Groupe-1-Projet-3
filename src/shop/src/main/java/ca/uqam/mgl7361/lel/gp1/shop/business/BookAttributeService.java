package ca.uqam.mgl7361.lel.gp1.shop.business;

import ca.uqam.mgl7361.lel.gp1.shop.business.mapper.BookAttributeMapper;
import ca.uqam.mgl7361.lel.gp1.shop.dto.AuthorDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookAttributeDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.CategoryDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.PublisherDTO;
import ca.uqam.mgl7361.lel.gp1.shop.model.BookAttribute;
import ca.uqam.mgl7361.lel.gp1.shop.model.Author;
import ca.uqam.mgl7361.lel.gp1.shop.model.Category;
import ca.uqam.mgl7361.lel.gp1.shop.model.Publisher;
import ca.uqam.mgl7361.lel.gp1.shop.persistence.BookAttributeDAO;
import ca.uqam.mgl7361.lel.gp1.shop.exception.AttributesException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.AuthorsException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.CategoriesException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.DTOException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.PublishersException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class BookAttributeService {

    private final BookAttributeDAO bookAttributeDAO = new BookAttributeDAO();

    public List<AuthorDTO> getAuthors() throws DTOException, AuthorsException {
        List<Author> authors = bookAttributeDAO.getAuthors();
        return authors.stream()
                .map(author -> new AuthorDTO(author.getId(), author.getName()))
                .collect(Collectors.toList());
    }

    public List<CategoryDTO> getCategories() throws DTOException, CategoriesException {

        List<Category> categories = bookAttributeDAO.getCategories();
        return categories.stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName()))
                .collect(Collectors.toList());

    }

    public List<PublisherDTO> getPublishers() throws DTOException, PublishersException {

        List<Publisher> publishers = bookAttributeDAO.getPublishers();
        return publishers.stream()
                .map(publisher -> new PublisherDTO(publisher.getId(), publisher.getName()))
                .collect(Collectors.toList());

    }

    public void addAttributes(List<BookAttributeDTO> bookAttributeDTOs) throws DTOException, AttributesException {

        List<BookAttribute> attributes = bookAttributeDTOs.stream()
                .map(BookAttributeMapper::toModel)
                .toList();

        bookAttributeDAO.addAttributes(attributes);

    }

    public void removeAttribute(BookAttributeDTO bookAttributeDTO) throws DTOException, AttributesException {

        BookAttribute bookAttribute = BookAttributeMapper.toModel(bookAttributeDTO);
        bookAttributeDAO.removeAttribute(bookAttribute);

    }
}
