package shop.business.mapper;

import shop.dto.BookDTO;
import shop.model.Book;

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

        return bookDTO;
    }
}
