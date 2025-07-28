package ca.uqam.mgl7361.lel.gp1.shop.business.mapper;

import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.AuthorDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.CategoryDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.PublisherDTO;
import ca.uqam.mgl7361.lel.gp1.shop.model.Book;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {
    public static Book toModel(BookDTO bookDTO) {
        Book book = new Book(
                bookDTO.getTitle(),
                bookDTO.getIsbn(),
                bookDTO.getPrice()
        );

        book.setDescription(bookDTO.getDescription());
        book.setPublicationDate(bookDTO.getPublicationDate());
        book.setStockQuantity(bookDTO.getStockQuantity());

        return book;
    }

    public static BookDTO toDTO(Book book) {
        BookDTO bookDTO = new BookDTO(
                book.getTitle(),
                book.getIsbn(),
                book.getPrice()
        );

        bookDTO.setDescription(book.getDescription());
        bookDTO.setPublicationDate(book.getPublicationDate());
        bookDTO.setStockQuantity(book.getStockQuantity());

        // Conversion List<Author> -> List<AuthorDTO>
        List<AuthorDTO> authorDTOs = book.getAuthors().stream()
                .map(a -> new AuthorDTO(a.getId(), a.getName()))
                .collect(Collectors.toList());
        bookDTO.setAuthors(authorDTOs);

        // Conversion List<Category> -> List<CategoryDTO>
        List<CategoryDTO> categoryDTOs = book.getCategories().stream()
                .map(c -> new CategoryDTO(c.getId(), c.getName()))
                .collect(Collectors.toList());
        bookDTO.setCategories(categoryDTOs);

        // Conversion Publisher -> PublisherDTO
        if (book.getPublisher() != null) {
            bookDTO.setPublisher(new PublisherDTO(book.getPublisher().getId(), book.getPublisher().getName()));
        }
        return bookDTO;
    }
}
