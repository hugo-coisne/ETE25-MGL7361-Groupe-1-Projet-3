package shop.business;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shop.dto.AuthorDTO;
import shop.dto.BookAttributeDTO;
import shop.dto.CategoryDTO;
import shop.dto.PublisherDTO;
import shop.exception.DTOException;
import shop.dto.BookProperty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookAttributeServiceTest {

    private BookAttributeService service;

    @BeforeEach
    public void setUp() {
        service = new BookAttributeService();
    }

    @AfterEach
    void clearAttributes() {
        for (String name : List.of("AuteurTest", "CategorieTest", "EditeurTest")) {
            for (BookProperty type : BookProperty.values()) {
                try {
                    service.removeAttribute(new BookAttributeDTO(name, type));
                } catch (Exception ignored) {}
            }
        }
    }

    @Test
    void testGetAuthorsReturnsCorrectData() {
        service.addAttributes(List.of(new BookAttributeDTO("AuteurTest", BookProperty.AUTHOR)));

        List<String> names = service.getAuthors().stream()
                .map(AuthorDTO::getName)
                .toList();

        assertTrue(names.contains("AuteurTest"));
    }

    @Test
    void testGetCategoriesReturnsCorrectData() {
        service.addAttributes(List.of(new BookAttributeDTO("CategorieTest", BookProperty.CATEGORY)));

        List<String> names = service.getCategories().stream()
                .map(CategoryDTO::getName)
                .toList();

        assertTrue(names.contains("CategorieTest"));
    }

    @Test
    void testGetPublishersReturnsCorrectData() {
        service.addAttributes(List.of(new BookAttributeDTO("EditeurTest", BookProperty.PUBLISHER)));

        List<String> names = service.getPublishers().stream()
                .map(PublisherDTO::getName)
                .toList();

        assertTrue(names.contains("EditeurTest"));
    }

    @Test
    public void testRemoveAttribute() throws DTOException {
        BookAttributeDTO category = new BookAttributeDTO("ToRemove Category", BookProperty.CATEGORY);
        service.addAttributes(List.of(category));

        // S'assurer qu'il est bien ajouté
        boolean exists = service.getCategories().stream()
                .anyMatch(c -> c.getName().equals("ToRemove Category"));
        assertTrue(exists);

        // Supprimer et vérifier
        service.removeAttribute(category);
        boolean stillExists = service.getCategories().stream()
                .anyMatch(c -> c.getName().equals("ToRemove Category"));
        assertFalse(stillExists);
    }
}
