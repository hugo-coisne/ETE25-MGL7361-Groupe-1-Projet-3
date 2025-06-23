package shop.business;

import shop.dto.BookDTO;
import shop.mapper.BookMapper;
import shop.model.Book;
import shop.dto.BookProperty;
import shop.persistence.BookDAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookService {
    private final BookDAO bookDAO;


    public BookService() {
        this.bookDAO = new BookDAO();
    }

    public BookDTO createBook(BookDTO bookDTO) throws Exception {
        Book book = BookMapper.toModel(bookDTO);

        this.bookDAO.save(book);

        return BookMapper.toDTO(book);
    }

    public List<BookDTO> getBooksBy(Map<BookProperty, String> criteria) throws Exception {
        // TODO : Add checking for criteria validity
        // TODO : Add the ability to retrieve publisher, categories and authors information, and add them to the Book then the BookDTO
        List<Book> books = this.bookDAO.getBooksBy(criteria);

        // Convert the list of Book objects to BookDTO objects
        return books.stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }
}
