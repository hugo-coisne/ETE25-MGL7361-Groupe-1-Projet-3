package ca.uqam.mgl7361.lel.gp1.shop.presentation;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.DBConnection;
import org.junit.jupiter.api.*;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookDTO;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookProperty;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookAPIImplTest {

    private BookAPI api;

    @BeforeEach
    void setUp() {
        api = new BookAPIImpl();
    }

    @AfterEach
    void cleanDatabase() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM book_author");
            stmt.executeUpdate("DELETE FROM book_category");
            stmt.executeUpdate("DELETE FROM books");
            stmt.executeUpdate("DELETE FROM authors");
            stmt.executeUpdate("DELETE FROM categories");
            stmt.executeUpdate("DELETE FROM publishers");
        }
    }

    @Test
    void testAddAndGetBook() throws Exception {
        BookDTO book = new BookDTO("Test Book", "ISBN_API", 15.99);
        api.addBook(book);

        List<BookDTO> results = api.getBooksBy(Map.of(BookProperty.ISBN, "ISBN_API"));
        assertEquals(1, results.size());

        BookDTO result = results.getFirst();
        assertEquals("Test Book", result.getTitle());
        assertEquals("ISBN_API", result.getIsbn());
        assertEquals(15.99, result.getPrice());

        api.deleteBook(book);
        List<BookDTO> afterDelete = api.getBooksBy(Map.of(BookProperty.ISBN, "ISBN_API"));
        assertTrue(afterDelete.isEmpty());
    }

    @Test
    void testSetAndRemoveProperties() throws Exception {
        BookDTO book = new BookDTO("Livre Test", "ISBN_PROPS", 9.99);
        api.createBook(book);

        Map<BookProperty, List<String>> props = Map.of(
                BookProperty.AUTHOR, List.of("Auteur Props"),
                BookProperty.CATEGORY, List.of("Catégorie Props"),
                BookProperty.PUBLISHER, List.of("Éditeur Props")
        );

        api.setPropertiesFor(book, props);
        List<BookDTO> afterSet = api.getBooksBy(Map.of(BookProperty.ISBN, "ISBN_PROPS"));
        BookDTO loaded = afterSet.getFirst();

        assertEquals("Auteur Props", loaded.getAuthors().getFirst().getName());
        assertEquals("Catégorie Props", loaded.getCategories().getFirst().getName());
        assertEquals("Éditeur Props", loaded.getPublisher().getName());

        api.removePropertiesFrom(book, props);
        List<BookDTO> afterRemove = api.getBooksBy(Map.of(BookProperty.ISBN, "ISBN_PROPS"));
        BookDTO cleaned = afterRemove.getFirst();

        assertTrue(cleaned.getAuthors() == null || cleaned.getAuthors().isEmpty());
        assertTrue(cleaned.getCategories() == null || cleaned.getCategories().isEmpty());
        assertNull(cleaned.getPublisher());
    }

    @Test
    void testIsInStock() throws Exception {
        BookDTO book = new BookDTO("Livre Stock", "ISBN_STOCK", 5.0);
        book.setStockQuantity(3);
        api.createBook(book);

        assertTrue(api.isInStock(book));
    }

    @Test
    void testIsSufficientlyInStock() throws Exception {
        BookDTO book = new BookDTO("Livre Stock Suffisant", "ISBN_SUFF", 5.0);
        book.setStockQuantity(5);
        api.createBook(book);

        assertTrue(api.isSufficientlyInStock(book, 4));
        assertFalse(api.isSufficientlyInStock(book, 6));
    }


}
