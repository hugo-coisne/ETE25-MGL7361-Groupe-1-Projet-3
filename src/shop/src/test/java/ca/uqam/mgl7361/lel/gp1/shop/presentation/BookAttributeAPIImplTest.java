package ca.uqam.mgl7361.lel.gp1.shop.presentation;

import org.junit.jupiter.api.*;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookAttributeDTO;
import ca.uqam.mgl7361.lel.gp1.shop.dto.BookProperty;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookAttributeAPIImplTest {

    private BookAttributeAPI api;

    @BeforeEach
    void setUp() {
        api = new BookAttributeAPIImpl();
    }

    @AfterEach
    void tearDown() {
        api.removeAttribute(new BookAttributeDTO("Auteur API", BookProperty.AUTHOR));
        api.removeAttribute(new BookAttributeDTO("Catégorie API", BookProperty.CATEGORY));
        api.removeAttribute(new BookAttributeDTO("Éditeur API", BookProperty.PUBLISHER));
    }

    @Test
    @Order(1)
    void testAddAndGetAttributes() {
        api.addAttributes(List.of(
                new BookAttributeDTO("Auteur API", BookProperty.AUTHOR),
                new BookAttributeDTO("Catégorie API", BookProperty.CATEGORY),
                new BookAttributeDTO("Éditeur API", BookProperty.PUBLISHER)
        ));

        var authors = api.getAuthors();
        var categories = api.getCategories();
        var publishers = api.getPublishers();

        assertTrue(authors.stream().anyMatch(a -> a.getName().equals("Auteur API")));
        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Catégorie API")));
        assertTrue(publishers.stream().anyMatch(p -> p.getName().equals("Éditeur API")));
    }

    @Test
    @Order(2)
    void testRemoveAttribute() {
        api.removeAttribute(new BookAttributeDTO("Auteur API", BookProperty.AUTHOR));
        var authors = api.getAuthors();
        assertFalse(authors.stream().anyMatch(a -> a.getName().equals("Auteur API")));
    }
}
