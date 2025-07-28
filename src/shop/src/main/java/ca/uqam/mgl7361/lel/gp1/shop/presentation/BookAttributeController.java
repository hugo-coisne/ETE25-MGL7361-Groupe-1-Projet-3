package ca.uqam.mgl7361.lel.gp1.shop.presentation;

import ca.uqam.mgl7361.lel.gp1.shop.business.BookAttributeService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookAttributeDTO;
import ca.uqam.mgl7361.lel.gp1.shop.exception.AttributesException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.AuthorsException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.CategoriesException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.DTOException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.PublishersException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attributes")
@Tag(name = "Book Attributes", description = "Endpoints for managing book-related metadata")
public class BookAttributeController {

    private static final Logger logger = LogManager.getLogger(BookAttributeController.class);
    private final BookAttributeService bookAttributeService;

    public BookAttributeController(BookAttributeService bookAttributeService) {
        this.bookAttributeService = bookAttributeService;
    }

    @Operation(summary = "Retrieve all authors")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authors retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Error retrieving authors")
    })
    @GetMapping("/authors")
    public ResponseEntity<?> getAuthors() {
        logger.info("Received request");
        try {
            return ResponseEntity.ok(bookAttributeService.getAuthors());
        } catch (DTOException e) {
            logger.error("Failed to retrieve authors", e);
            return ResponseEntity.status(500).build();
        } catch (AuthorsException e) {
            logger.error("Authors exception occurred", e);
            return ResponseEntity.status(500).body(Map.of("Error retrieving authors: ", e.getMessage()));
        }
    }

    @Operation(summary = "Retrieve all categories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Error retrieving categories")
    })
    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        logger.info("Received request");
        try {
            return ResponseEntity.ok(bookAttributeService.getCategories());
        } catch (DTOException e) {
            logger.error("Failed to retrieve categories", e);
            return ResponseEntity.status(500).build();
        } catch (CategoriesException e) {
            logger.error("Categories exception occurred", e);
            return ResponseEntity.status(500).body(Map.of("Error retrieving categories: ", e.getMessage()));
        }
    }

    @Operation(summary = "Retrieve all publishers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publishers retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Error retrieving publishers")
    })
    @GetMapping("/publishers")
    public ResponseEntity<?> getPublishers() {
        logger.info("Received request");
        try {
            return ResponseEntity.ok(bookAttributeService.getPublishers());
        } catch (DTOException e) {
            logger.error("Failed to retrieve publishers", e);
            return ResponseEntity.status(500).build();
        } catch (PublishersException e) {
            logger.error("Publishers exception occurred", e);
            return ResponseEntity.status(500).body(Map.of("Error retrieving publishers: ", e.getMessage()));
        }
    }

    @Operation(summary = "Add multiple book attributes (authors, publishers, categories)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Attributes added successfully"),
            @ApiResponse(responseCode = "500", description = "Error adding attributes")
    })
    @PostMapping
    public ResponseEntity<?> addAttributes(
            @RequestBody(description = "List of attributes to add", required = true, content = @Content(schema = @Schema(implementation = BookAttributeDTO.class))) @org.springframework.web.bind.annotation.RequestBody List<BookAttributeDTO> attributes) {
        logger.info("Received request with attributes : {}", attributes);
        try {
            bookAttributeService.addAttributes(attributes);
            return ResponseEntity.ok().build();
        } catch (DTOException e) {
            logger.error("Failed to add attributes", e);
            return ResponseEntity.status(500).body(e.getMessage());
        } catch (AttributesException e) {
            logger.error("Attributes exception occurred", e);
            return ResponseEntity.status(500).body(Map.of("Error adding attributes: ", e.getMessage()));
        }
    }

    @Operation(summary = "Remove a specific attribute (author, publisher, category)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Attribute removed successfully"),
            @ApiResponse(responseCode = "500", description = "Error removing attribute")
    })
    @DeleteMapping
    public ResponseEntity<?> removeAttribute(
            @RequestBody(description = "Attribute to remove", required = true, content = @Content(schema = @Schema(implementation = BookAttributeDTO.class))) @org.springframework.web.bind.annotation.RequestBody BookAttributeDTO attribute) {
        logger.info("Received request with attribute : {}", attribute);
        try {
            bookAttributeService.removeAttribute(attribute);
            return ResponseEntity.ok().build();
        } catch (DTOException e) {
            logger.error("Failed to remove attribute", e);
            return ResponseEntity.status(500).body(e.getMessage());
        } catch (AttributesException e) {
            logger.error("Attributes exception occurred", e);
            return ResponseEntity.status(500).body(Map.of("Error removing attribute: ", e.getMessage()));
        }
    }
}
