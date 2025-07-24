package ca.uqam.mgl7361.lel.gp1.common.clients;

import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.AuthorDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookAttributeDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.CategoryDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.PublisherDTO;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

public interface BookAttributeAPIClient {

    @RequestLine("GET /attributes/authors")
    List<AuthorDTO> getAuthors();

    @RequestLine("GET /attributes/categories")
    List<CategoryDTO> getCategories();

    @RequestLine("GET /attributes/publishers")
    List<PublisherDTO> getPublishers();

    @RequestLine("POST /attributes")
    @Headers("Content-Type: application/json")
    void addAttributes(List<BookAttributeDTO> attributes);

    @RequestLine("DELETE /attributes")
    @Headers("Content-Type: application/json")
    void removeAttribute(BookAttributeDTO attribute);
}