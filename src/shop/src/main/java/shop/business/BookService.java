package shop.business;

import shop.dto.BookDTO;
import shop.model.Book;
import shop.persistence.BookDAO;

public class BookService {
    private final BookDAO bookDAO;


    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public Book createBook(BookDTO bookDTO) throws Exception {
        Book book = new Book(
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getIsbn(),
                bookDTO.getPrice()
        );

        this.bookDAO.save(book);

        return book;
    }
}
