package ca.uqam.mgl7361.lel.gp1.shop.persistence;

import org.junit.jupiter.api.*;
import ca.uqam.mgl7361.lel.gp1.shop.model.Book;
import ca.uqam.mgl7361.lel.gp1.common.DBConnection;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookProperty;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BookDAOTest {

    private BookDAO bookDAO;

    @BeforeEach
    void cleanDatabase() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            conn.prepareStatement("DELETE FROM book_author").executeUpdate();
            conn.prepareStatement("DELETE FROM book_category").executeUpdate();
            //conn.prepareStatement("DELETE FROM cart_book").executeUpdate(); // si présent
            //conn.prepareStatement("DELETE FROM carts").executeUpdate(); // si présent
            conn.prepareStatement("DELETE FROM books").executeUpdate();
            conn.prepareStatement("DELETE FROM authors").executeUpdate();
            conn.prepareStatement("DELETE FROM categories").executeUpdate();
            conn.prepareStatement("DELETE FROM publishers").executeUpdate();

            conn.commit();
            bookDAO = new BookDAO();
        }
    }


    @AfterEach
    void cleanup() throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            String[] testIsbns = {
                    "PLENTYISBN123", "ISBN_TEST", "ISBNTEST123", "STOCKISBN123"
            };

            for (String isbn : testIsbns) {
                // Supprimer liaisons
                try (PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM book_author WHERE book_id = (SELECT id FROM books WHERE isbn = ?)")) {
                    ps.setString(1, isbn);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM book_category WHERE book_id = (SELECT id FROM books WHERE isbn = ?)")) {
                    ps.setString(1, isbn);
                    ps.executeUpdate();
                }
                // Supprimer le livre
                try (PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM books WHERE isbn = ?")) {
                    ps.setString(1, isbn);
                    ps.executeUpdate();
                }
            }

            // Supprimer les propriétés (facultatif mais propre)
            conn.prepareStatement("DELETE FROM authors WHERE name LIKE 'Auteur Test%'").executeUpdate();
            conn.prepareStatement("DELETE FROM categories WHERE name LIKE 'Catégorie Test%'").executeUpdate();
            conn.prepareStatement("DELETE FROM publishers WHERE name LIKE 'Éditeur Test%'").executeUpdate();

            conn.commit();
        }
    }



    @Test
    void testSaveAndGetBooksBy() throws Exception {
        Book book = new Book("Test Book", "ISBNTEST123", 10.99);
        book.setDescription("desc");
        book.setPublicationDate(Date.valueOf("2022-01-01"));
        book.setStockQuantity(5);

        bookDAO.save(book);

        Map<BookProperty, String> criteria = new HashMap<>();
        criteria.put(BookProperty.TITLE, "Test Book");
        List<Book> books = bookDAO.getBooksBy(criteria);

        assertFalse(books.isEmpty());
        Book result = books.getFirst();
        assertEquals("Test Book", result.getTitle());
        assertEquals("ISBNTEST123", result.getIsbn());
    }

    @Test
    void testIsInStock() throws Exception {
        Book book = new Book("Stock Book", "STOCKISBN123", 12.50);
        book.setStockQuantity(3);
        bookDAO.save(book);

        assertTrue(bookDAO.isInStock(book));
    }

    @Test
    void testIsSufficientlyInStock() throws Exception {
        Book book = new Book("Plenty Book", "PLENTYISBN123", 15.00);
        book.setStockQuantity(10);
        bookDAO.save(book);

        assertTrue(bookDAO.isSufficientlyInStock(book, 5));
        assertFalse(bookDAO.isSufficientlyInStock(book, 20));
    }

    @Test
    void testSetAndRemoveProperties() throws Exception {

        // Création du livre
        Book book = new Book("Test Book", "ISBN_TEST", 12.99);
        bookDAO.save(book); // on suppose que cette méthode existe déjà

        // Définir des propriétés
        Map<BookProperty, List<String>> properties = new HashMap<>();
        properties.put(BookProperty.AUTHOR, List.of("Auteur Test"));
        properties.put(BookProperty.CATEGORY, List.of("Catégorie Test"));
        properties.put(BookProperty.PUBLISHER, List.of("Éditeur Test"));

        bookDAO.setPropertiesFor(book, properties);

        Map<BookProperty, String> isbnCriteria = new HashMap<>();
        isbnCriteria.put(BookProperty.ISBN, "ISBN_TEST");
        List<Book> foundBooks = bookDAO.getBooksBy(isbnCriteria);

        
        assertEquals(1, foundBooks.size());
        Book loaded = foundBooks.getFirst();

        assertEquals("Test Book", loaded.getTitle());
        assertEquals("ISBN_TEST", loaded.getIsbn());

        assertEquals(1, loaded.getCategories().size());
        assertEquals("Catégorie Test", loaded.getCategories().getFirst().getName());

        assertEquals(1, loaded.getAuthors().size());
        assertEquals("Auteur Test", loaded.getAuthors().getFirst().getName());

        assertNotNull(loaded.getPublisher());
        assertEquals("Éditeur Test", loaded.getPublisher().getName());

        // Supprimer les propriétés
        bookDAO.removePropertiesFrom(book, properties);

        // Recharger à nouveau pour vérifier que les associations ont été retirées
        List<Book> afterRemoval = bookDAO.getBooksBy(isbnCriteria);
        assertEquals(1, afterRemoval.size());
        Book reloaded = afterRemoval.getFirst();

        assertTrue( reloaded.getAuthors() == null || reloaded.getAuthors().isEmpty());
        assertTrue( reloaded.getCategories() == null || reloaded.getCategories().isEmpty());
        assertNull( reloaded.getPublisher() );

        bookDAO.deleteBook(book);

        // Vérification que le livre est bien supprimé
        List<Book> afterDelete = bookDAO.getBooksBy(isbnCriteria);
        assertTrue(afterDelete.isEmpty());
    }

}
