package ca.uqam.mgl7361.lel.gp1.shop.presentation;

import ca.uqam.mgl7361.lel.gp1.shop.business.BookAttributeService;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookAttributeDTO;
import ca.uqam.mgl7361.lel.gp1.shop.exception.AttributesException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.AuthorsException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.CategoriesException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.DTOException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.PublishersException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attributes")
public class BookAttributeController {

    private static final Logger logger = LogManager.getLogger(BookAttributeController.class);

    private final BookAttributeService bookAttributeService;

    public BookAttributeController(BookAttributeService bookAttributeService) {
        this.bookAttributeService = bookAttributeService;
    }

    @GetMapping("/authors")
    public ResponseEntity<?> getAuthors() {
        try {
            logger.info("Retrieving all authors...");
            return ResponseEntity.ok(bookAttributeService.getAuthors());
        } catch (DTOException e) {
            logger.error("Failed to retrieve authors", e);
            return ResponseEntity.status(500).build();
        } catch (AuthorsException e) {
            logger.error("Authors exception occurred", e);
            return ResponseEntity.status(500).body(Map.of("Error retrieving authors: ", e.getMessage()));
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        try {
            logger.info("Retrieving all categories...");
            return ResponseEntity.ok(bookAttributeService.getCategories());
        } catch (DTOException e) {
            logger.error("Failed to retrieve categories", e);
            return ResponseEntity.status(500).build();
        } catch (CategoriesException e) {
            logger.error("Categories exception occurred", e);
            return ResponseEntity.status(500).body(Map.of("Error retrieving categories: ", e.getMessage()));
        }
    }

    @GetMapping("/publishers")
    public ResponseEntity<?> getPublishers() {
        try {
            logger.info("Retrieving all publishers...");
            return ResponseEntity.ok(bookAttributeService.getPublishers());
        } catch (DTOException e) {
            logger.error("Failed to retrieve publishers", e);
            return ResponseEntity.status(500).build();
        } catch (PublishersException e) {
            logger.error("Publishers exception occurred", e);
            return ResponseEntity.status(500).body(Map.of("Error retrieving publishers: ", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> addAttributes(@RequestBody List<BookAttributeDTO> attributes) {
        try {
            logger.info("Adding attributes: {}", attributes);
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

    @DeleteMapping
    public ResponseEntity<?> removeAttribute(@RequestBody BookAttributeDTO attribute) {
        try {
            logger.info("Removing attribute: {}", attribute);
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
