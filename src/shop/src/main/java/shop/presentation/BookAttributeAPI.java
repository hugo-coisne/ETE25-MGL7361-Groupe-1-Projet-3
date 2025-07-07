package shop.presentation;

import shop.dto.BookAttributeDTO;
import shop.exception.DTOException;
import shop.dto.AuthorDTO;
import shop.dto.CategoryDTO;
import shop.dto.PublisherDTO;

import java.util.List;

public interface BookAttributeAPI {
    List<AuthorDTO> getAuthors() throws DTOException;
    List<CategoryDTO> getCategories() throws DTOException;
    List<PublisherDTO> getPublishers() throws DTOException;

    void addAttributes(List<BookAttributeDTO> bookAttributesDTO) throws DTOException;
    void removeAttribute(BookAttributeDTO bookAttributeDTO) throws DTOException;
}
