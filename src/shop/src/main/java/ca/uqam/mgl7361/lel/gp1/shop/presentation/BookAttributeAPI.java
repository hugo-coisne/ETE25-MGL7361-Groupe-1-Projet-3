package ca.uqam.mgl7361.lel.gp1.shop.presentation;

import ca.uqam.mgl7361.lel.gp1.shop.dto.BookAttributeDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.AuthorDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.CategoryDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.PublisherDTO;

import java.util.List;

public interface BookAttributeAPI {
    List<AuthorDTO> getAuthors() throws Exception;
    List<CategoryDTO> getCategories() throws Exception;
    List<PublisherDTO> getPublishers() throws Exception;

    void addAttributes(List<BookAttributeDTO> bookAttributesDTO) throws Exception;
    void removeAttribute(BookAttributeDTO bookAttributeDTO) throws Exception;
}
