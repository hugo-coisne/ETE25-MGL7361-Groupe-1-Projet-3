package ca.uqam.mgl7361.lel.gp1.shop.model;

import org.junit.jupiter.api.Test;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookProperty;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    void testAuthor() {
        Author author = new Author(1, "J.K. Rowling");
        assertEquals(1, author.getId());
        assertEquals("J.K. Rowling", author.getName());
        assertEquals(BookProperty.AUTHOR, author.getType());
    }

    @Test
    void testCategory() {
        Category category = new Category(2, "Fantasy");
        assertEquals(2, category.getId());
        assertEquals("Fantasy", category.getName());
        assertEquals(BookProperty.CATEGORY, category.getType());
    }

    @Test
    void testPublisher() {
        Publisher publi = new Publisher("Flammarion");
        assertEquals("Flammarion", publi.getName());
        Publisher publisher = new Publisher(3, "Gallimard");
        assertEquals(3, publisher.getId());
        assertEquals("Gallimard", publisher.getName());
        assertEquals(BookProperty.PUBLISHER, publisher.getType());
    }

    @Test
    void testBook() {
        Book book = new Book("Clean Code", "ISBN98765", 34.99);
        book.setDescription("A book about writing cleaner Java code");
        book.setPublicationDate(java.sql.Date.valueOf("2008-08-01"));
        book.setStockQuantity(50);

        assertEquals("Clean Code", book.getTitle());
        assertEquals("ISBN98765", book.getIsbn());
        assertEquals(34.99, book.getPrice());
        assertEquals("A book about writing cleaner Java code", book.getDescription());
        assertEquals(java.sql.Date.valueOf("2008-08-01"), book.getPublicationDate());
        assertEquals(50, book.getStockQuantity());
    }
}
