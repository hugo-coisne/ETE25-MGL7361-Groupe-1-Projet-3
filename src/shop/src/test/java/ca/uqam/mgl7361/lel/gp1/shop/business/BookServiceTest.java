package ca.uqam.mgl7361.lel.gp1.shop.business;

import org.junit.jupiter.api.*;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookProperty;
import ca.uqam.mgl7361.lel.gp1.shop.exception.DTOException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.DuplicationBookException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookServiceTest {

    private BookService service;

    @BeforeAll
    void setup() {
        service = new BookService();
    }

    @BeforeEach
    void clean() throws DTOException {
        // Nettoyage : on supprime les livres de test s’ils existent
        try {
            BookDTO toDelete = new BookDTO("Test Book", "ISBN_TEST", 10.99);
            service.deleteBook(toDelete);
        } catch (DTOException ignored) {
        }
    }

    @Test
    void testCreateAndGetBookBy() {
        BookDTO dto = new BookDTO("Test Book", "ISBN_TEST", 10.99);
        service.createBook(dto);

        Map<BookProperty, String> criteria = new HashMap<>();
        criteria.put(BookProperty.ISBN, "ISBN_TEST");

        List<BookDTO> found = service.getBooksBy(criteria);

        assertEquals(1, found.size());
        assertEquals("Test Book", found.getFirst().getTitle());
    }

    @Test
    void testSetAndRemoveProperties() {
        BookDTO dto = new BookDTO("Test Book", "ISBN_TEST", 10.99);
        service.addBook(dto);

        // Définir des propriétés
        Map<BookProperty, List<String>> properties = new HashMap<>();
        properties.put(BookProperty.AUTHOR, List.of("Auteur Test"));
        properties.put(BookProperty.CATEGORY, List.of("Catégorie Test"));
        properties.put(BookProperty.PUBLISHER, List.of("Éditeur Test"));

        service.setPropertiesFor(dto, properties);

        // Vérifier que les propriétés ont bien été ajoutées
        List<BookDTO> afterSet = service.getBooksBy(Map.of(BookProperty.ISBN, "ISBN_TEST"));

        assertFalse(afterSet.isEmpty());
        BookDTO loaded = afterSet.getFirst();
        assertEquals("Catégorie Test", loaded.getCategories().getFirst().getName());
        assertEquals("Éditeur Test", loaded.getPublisher().getName());
        assertEquals("Auteur Test", loaded.getAuthors().getFirst().getName());

        // Retrait
        service.removePropertiesFrom(dto, properties);

        // Vérifier qu'elles ont bien été retirées
        List<BookDTO> afterRemove = service.getBooksBy(Map.of(BookProperty.ISBN, "ISBN_TEST"));
        BookDTO cleaned = afterRemove.getFirst();
        assertTrue(cleaned.getAuthors() == null || cleaned.getAuthors().isEmpty());
        assertTrue(cleaned.getCategories() == null || cleaned.getCategories().isEmpty());
        assertNull(cleaned.getPublisher());

        service.deleteBook(dto);

        // Vérifier qu’il n’est plus dans la base
        Map<BookProperty, String> criteria = Map.of(BookProperty.ISBN, "ISBN_TEST");
        List<BookDTO> books = service.getBooksBy(criteria);
        assertTrue(books.isEmpty());
    }

    @Test
    void testInStock() {
        BookDTO dto = new BookDTO("Stock Book", "ISBN_STOCK", 9.99);
        dto.setStockQuantity(3);
        service.addBook(dto);

        assertTrue(service.isInStock(dto));

        dto.setStockQuantity(0);
        service.deleteBook(dto);
    }

    @Test
    void testDuplicateBook() {
        BookDTO dto = new BookDTO("Duplicate", "ISBN_DUPL", 9.99);
        assertDoesNotThrow(() -> service.createBook(dto));
        assertThrows(DuplicationBookException.class, () -> service.createBook(dto));

        assertDoesNotThrow(() -> service.deleteBook(dto));
    }

    @Test
    void testIsSufficientlyInStock() {
        BookDTO dto = new BookDTO("Stock Book", "ISBN_STOCK", 12.99);
        dto.setStockQuantity(5);
        service.addBook(dto);

        assertTrue(service.isSufficientlyInStock(dto, 3));
        assertTrue(service.isSufficientlyInStock(dto, 5));
        assertFalse(service.isSufficientlyInStock(dto, 6));

        service.deleteBook(dto);
    }

}
