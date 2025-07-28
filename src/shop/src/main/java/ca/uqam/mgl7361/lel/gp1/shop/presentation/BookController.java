package ca.uqam.mgl7361.lel.gp1.shop.presentation;

import ca.uqam.mgl7361.lel.gp1.shop.business.BookService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookStockQuantityRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookPropertiesRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookProperty;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
@Tag(name = "Books", description = "Endpoints for managing books and inventory")
public class BookController {

    private final BookService bookService;
    private static final Logger logger = LogManager.getLogger(BookController.class);

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Search books by properties", description = "Retrieve a list of books matching the given property-value criteria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Books found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/search")
    public ResponseEntity<List<BookDTO>> getBooksBy(
            @org.springframework.web.bind.annotation.RequestBody Map<BookProperty, String> criteria) {
        logger.info("Received request with criteria: {}", criteria);
        try {
            List<BookDTO> books = bookService.getBooksBy(criteria);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            logger.error("Error retrieving books by criteria", e);
            return ResponseEntity.status(500).body(List.of());
        }
    }

    @Operation(summary = "Create a new book", description = "Creates and returns a new book with provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> createBook(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Book to create", required = true, content = @Content(schema = @Schema(implementation = BookDTO.class))) @org.springframework.web.bind.annotation.RequestBody BookDTO bookDTO) {
        logger.info("Received request with {}", bookDTO);
        try {
            BookDTO createdBook = bookService.createBook(bookDTO);
            return ResponseEntity.ok(createdBook);
        } catch (Exception e) {
            logger.error("Error creating book", e);
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Add a book to catalog")
    @PostMapping("/add")
    public ResponseEntity<?> addBook(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Book to add", required = true, content = @Content(schema = @Schema(implementation = BookDTO.class))) @org.springframework.web.bind.annotation.RequestBody BookDTO bookDTO) {
        logger.info("Adding book to catalog: {}", bookDTO.getTitle());
        try {
            bookService.addBook(bookDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error adding book", e);
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Delete a book from the catalog")
    @DeleteMapping
    public ResponseEntity<?> deleteBook(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Book to delete", required = true, content = @Content(schema = @Schema(implementation = BookDTO.class))) @org.springframework.web.bind.annotation.RequestBody BookDTO bookDTO) {
        try {
            bookService.deleteBook(bookDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error deleting book", e);
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Set properties for a book")
    @PostMapping("/properties/set")
    public ResponseEntity<Void> setPropertiesFor(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request to set properties", required = true, content = @Content(schema = @Schema(implementation = BookPropertiesRequest.class))) @org.springframework.web.bind.annotation.RequestBody BookPropertiesRequest request) {
        bookService.setPropertiesFor(request.book(), request.properties());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove properties from a book")
    @PostMapping("/properties/remove")
    public ResponseEntity<Void> removePropertiesFrom(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request to remove properties", required = true, content = @Content(schema = @Schema(implementation = BookPropertiesRequest.class))) @org.springframework.web.bind.annotation.RequestBody BookPropertiesRequest request) {
        bookService.removePropertiesFrom(request.book(), request.properties());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Check if a book is in stock")
    @PostMapping("/is-in-stock")
    public ResponseEntity<Boolean> isInStock(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Book to check stock", required = true, content = @Content(schema = @Schema(implementation = BookDTO.class))) @org.springframework.web.bind.annotation.RequestBody BookDTO bookDTO) {
        boolean inStock = bookService.isInStock(bookDTO);
        return ResponseEntity.ok(inStock);
    }

    @Operation(summary = "Check if enough quantity is in stock")
    @PostMapping("/quantity-in-stock")
    public ResponseEntity<Boolean> isSufficientlyInStock(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request containing book and quantity", required = true, content = @Content(schema = @Schema(implementation = BookStockQuantityRequest.class))) @org.springframework.web.bind.annotation.RequestBody BookStockQuantityRequest bookStockQuantityRequest) {
        boolean sufficient = bookService.isSufficientlyInStock(
                bookStockQuantityRequest.book(), bookStockQuantityRequest.quantity());
        return ResponseEntity.ok(sufficient);
    }

    @Operation(summary = "Decrease book stock quantity")
    @DeleteMapping("/stock/decrease")
    public ResponseEntity<?> decreaseBookStockQuantity(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request containing book and quantity to decrease", required = true, content = @Content(schema = @Schema(implementation = BookStockQuantityRequest.class))) @org.springframework.web.bind.annotation.RequestBody BookStockQuantityRequest bookStockQuantityRequest) {
        try {
            bookService.decreaseBookStockQuantity(
                    bookStockQuantityRequest.book(), bookStockQuantityRequest.quantity());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error decreasing stock", e);
            return ResponseEntity.internalServerError().body(e);
        }
    }
}
