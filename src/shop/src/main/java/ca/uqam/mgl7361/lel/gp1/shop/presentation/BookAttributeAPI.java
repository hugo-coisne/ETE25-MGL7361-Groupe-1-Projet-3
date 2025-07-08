package ca.uqam.mgl7361.lel.gp1.shop.presentation;

import ca.uqam.mgl7361.lel.gp1.shop.dto.BookAttributeDTO;
import ca.uqam.mgl7361.lel.gp1.shop.exception.DTOException;
import ca.uqam.mgl7361.lel.gp1.shop.dto.AuthorDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.CategoryDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.PublisherDTO;

import java.util.List;

public interface BookAttributeAPI {
    List<AuthorDTO> getAuthors() throws DTOException;
    List<CategoryDTO> getCategories() throws DTOException;
    List<PublisherDTO> getPublishers() throws DTOException;

    void addAttributes(List<BookAttributeDTO> bookAttributesDTO) throws DTOException;
    void removeAttribute(BookAttributeDTO bookAttributeDTO) throws DTOException;
}
