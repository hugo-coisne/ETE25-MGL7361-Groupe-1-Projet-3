package ca.uqam.mgl7361.lel.gp1.shop.presentation;

import io.swagger.v3.oas.annotations.Operation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.uqam.mgl7361.lel.gp1.shop.business.BookService;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookProperty;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private static final Logger logger = LogManager.getLogger(BookController.class);

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get books by criteria")
    @PostMapping("/search")
    public ResponseEntity<List<BookDTO>> getBooksBy(@RequestBody Map<BookProperty, String> criteria) {
        logger.info("Searching books with criteria: {}", criteria);
        List<BookDTO> books = bookService.getBooksBy(criteria);
        logger.info("Found {} books matching criteria", books.size());
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Create a new book")
    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookDTO bookDTO) {
        logger.info("Creating new book: {}", bookDTO.getTitle());
        try {
            BookDTO createdBook = bookService.createBook(bookDTO);
            logger.info("Book created successfully with title: {}", createdBook.getTitle());
            return ResponseEntity.ok(createdBook);
        } catch (Exception e) {
            logger.error("Error creating book: {}", bookDTO.getTitle(), e);
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Add a book (used for re-adding an existing book to the catalog)")
    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BookDTO bookDTO) {
        logger.info("Adding book to catalog: {}", bookDTO.getTitle());
        try {
            bookService.addBook(bookDTO);
            logger.info("Book added to catalog successfully: {}", bookDTO.getTitle());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error adding book: {}", bookDTO.getTitle(), e);
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Delete a book")
    @DeleteMapping
    public ResponseEntity<?> deleteBook(@RequestBody BookDTO bookDTO) {
        logger.info("Deleting book: {}", bookDTO.getTitle());
        try {
            bookService.deleteBook(bookDTO);
            logger.info("Book deleted successfully: {}", bookDTO.getTitle());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting book: {}", e);
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Set properties for a book")
    @PostMapping("/properties/set")
    public ResponseEntity<Void> setPropertiesFor(@RequestBody BookPropertiesRequest request) {
        logger.info("Setting properties {} for book: {}", request.properties(), request.book().getTitle());
        bookService.setPropertiesFor(request.book(), request.properties());
        logger.info("Properties set successfully for book: {}", request.book().getTitle());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove properties from a book")
    @PostMapping("/properties/remove")
    public ResponseEntity<Void> removePropertiesFrom(@RequestBody BookPropertiesRequest request) {
        logger.info("Removing properties {} from book: {}", request.properties(), request.book().getTitle());
        bookService.removePropertiesFrom(request.book(), request.properties());
        logger.info("Properties removed successfully from book: {}", request.book().getTitle());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Check if a book is in stock")
    @PostMapping("/in-stock")
    public ResponseEntity<Boolean> isInStock(@RequestBody BookDTO bookDTO) {
        boolean inStock = bookService.isInStock(bookDTO);
        logger.info("Book '{}' in stock: {}", bookDTO.getTitle(), inStock);
        return ResponseEntity.ok(inStock);
    }

    @Operation(summary = "Check if a book is in stock with given quantity")
    @PostMapping("/in-stock/{quantity}")
    public ResponseEntity<Boolean> isSufficientlyInStock(
            @RequestBody BookDTO bookDTO,
            @PathVariable int quantity) {
        boolean sufficient = bookService.isSufficientlyInStock(bookDTO, quantity);
        logger.info("Book '{}' in stock with quantity {}: {}", bookDTO.getTitle(), quantity, sufficient);
        return ResponseEntity.ok(sufficient);
    }

    // DTO for requests with a book and its properties
    public record BookPropertiesRequest(
            BookDTO book,
            Map<BookProperty, List<String>> properties) {
    }
}
