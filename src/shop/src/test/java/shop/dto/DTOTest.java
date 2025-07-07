package shop.dto;

import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DTOTest {

    @Test
    void testBookDTO() {
        BookDTO dto = new BookDTO("Clean Code", "ISBN98765", 34.99);
        dto.setDescription("A book about writing cleaner Java code");
        dto.setPublicationDate(Date.valueOf("2008-08-01"));
        dto.setStockQuantity(50);

        assertEquals("Clean Code", dto.getTitle());
        assertEquals("ISBN98765", dto.getIsbn());
        assertEquals(34.99, dto.getPrice());
        assertEquals("A book about writing cleaner Java code", dto.getDescription());
        assertEquals(Date.valueOf("2008-08-01"), dto.getPublicationDate());
        assertEquals(50, dto.getStockQuantity());
    }

    @Test
    void testAuthorDTO() {
        AuthorDTO dto = new AuthorDTO(1, "Victor Hugo");
        assertEquals(1, dto.getId());
        assertEquals("Victor Hugo", dto.getName());

        dto.setId(2);
        dto.setName("Zola");
        assertEquals(2, dto.getId());
        assertEquals("Zola", dto.getName());
    }

    @Test
    void testPublisherDTO() {
        PublisherDTO dto = new PublisherDTO(10, "Gallimard");
        assertEquals(10, dto.getId());
        assertEquals("Gallimard", dto.getName());

        dto.setId(11);
        dto.setName("Flammarion");
        assertEquals(11, dto.getId());
        assertEquals("Flammarion", dto.getName());
    }

    @Test
    void testCategoryDTO() {
        CategoryDTO dto = new CategoryDTO(5, "Science Fiction");
        assertEquals(5, dto.getId());
        assertEquals("Science Fiction", dto.getName());

        dto.setId(6);
        dto.setName("Fantasy");
        assertEquals(6, dto.getId());
        assertEquals("Fantasy", dto.getName());
    }

    @Test
    void testBookAttributeDTO() {
        BookAttributeDTO dto = new BookAttributeDTO(3, "Romance", BookProperty.CATEGORY);
        assertEquals(3, dto.getId());
        assertEquals("Romance", dto.getName());
        assertEquals(BookProperty.CATEGORY, dto.getType());

        dto.setId(4);
        dto.setName("History");
        dto.setType(BookProperty.CATEGORY);
        assertEquals(4, dto.getId());
        assertEquals("History", dto.getName());
        assertEquals(BookProperty.CATEGORY, dto.getType());
    }
}
