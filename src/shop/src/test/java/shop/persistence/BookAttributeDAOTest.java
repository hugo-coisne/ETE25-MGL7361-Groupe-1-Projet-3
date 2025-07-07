package shop.persistence;

import org.junit.jupiter.api.*;
import shop.model.Author;
import shop.model.Category;
import shop.model.Publisher;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookAttributeDAOTest {

    private BookAttributeDAO dao;

    @BeforeAll
    void setup() {
        dao = new BookAttributeDAO();
    }

    @BeforeEach
    void cleanDatabase() throws Exception {
        try (Connection conn = common.DBConnection.getConnection()) {
            conn.prepareStatement("DELETE FROM cart_book").executeUpdate();      // Dépend de books
            conn.prepareStatement("DELETE FROM book_author").executeUpdate();    // Dépend de books et authors
            conn.prepareStatement("DELETE FROM book_category").executeUpdate();  // Dépend de books et categories
            conn.prepareStatement("DELETE FROM books").executeUpdate();
            conn.prepareStatement("DELETE FROM authors").executeUpdate();
            conn.prepareStatement("DELETE FROM categories").executeUpdate();
            conn.prepareStatement("DELETE FROM publishers").executeUpdate();
        }
    }


    @Test
    void testAddAndRetrieveAuthors() throws Exception {
        Author a1 = new Author("Test Author 1");
        Author a2 = new Author("Test Author 2");

        dao.addAttributes(List.of(a1, a2));

        List<Author> authors = dao.getAuthors();
        assertEquals(2, authors.size());
        assertTrue(authors.stream().anyMatch(a -> a.getName().equals("Test Author 1")));
        assertTrue(authors.stream().anyMatch(a -> a.getName().equals("Test Author 2")));
    }

    @Test
    void testAddAndRetrieveCategories() throws Exception {
        Category c1 = new Category("Fantasy");
        Category c2 = new Category("Sci-Fi");

        dao.addAttributes(List.of(c1, c2));

        List<Category> categories = dao.getCategories();
        assertEquals(2, categories.size());
        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Fantasy")));
        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Sci-Fi")));
    }

    @Test
    void testAddAndRetrievePublishers() throws Exception {
        Publisher p1 = new Publisher("Publisher A");
        Publisher p2 = new Publisher("Publisher B");

        dao.addAttributes(List.of(p1, p2));

        List<Publisher> publishers = dao.getPublishers();
        assertEquals(2, publishers.size());
        assertTrue(publishers.stream().anyMatch(p -> p.getName().equals("Publisher A")));
        assertTrue(publishers.stream().anyMatch(p -> p.getName().equals("Publisher B")));
    }

    @Test
    void testRemoveAuthorAttribute() throws Exception {
        Author author = new Author("Removable Author");
        dao.addAttributes(List.of(author));

        List<Author> beforeRemoval = dao.getAuthors();
        assertTrue(beforeRemoval.stream().anyMatch(a -> a.getName().equals("Removable Author")));

        dao.removeAttribute(author);

        List<Author> afterRemoval = dao.getAuthors();
        assertFalse(afterRemoval.stream().anyMatch(a -> a.getName().equals("Removable Author")));
    }

    @Test
    void testRemoveCategoryAttribute() throws Exception {
        Category category = new Category("Removable Category");
        dao.addAttributes(List.of(category));

        List<Category> before = dao.getCategories();
        assertTrue(before.stream().anyMatch(c -> c.getName().equals("Removable Category")));

        dao.removeAttribute(category);

        List<Category> after = dao.getCategories();
        assertFalse(after.stream().anyMatch(c -> c.getName().equals("Removable Category")));
    }

    @Test
    void testRemovePublisherAttribute() throws Exception {
        Publisher publisher = new Publisher("Removable Publisher");
        dao.addAttributes(List.of(publisher));

        List<Publisher> before = dao.getPublishers();
        assertTrue(before.stream().anyMatch(p -> p.getName().equals("Removable Publisher")));

        dao.removeAttribute(publisher);

        List<Publisher> after = dao.getPublishers();
        assertFalse(after.stream().anyMatch(p -> p.getName().equals("Removable Publisher")));
    }

}
