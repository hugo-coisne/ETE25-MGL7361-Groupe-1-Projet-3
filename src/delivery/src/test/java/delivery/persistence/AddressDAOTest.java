package delivery.persistence;

import delivery.model.Address;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import common.DBConnection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class AddressDAOTest {

    private static final Connection connection;

    static {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static AddressDAO addressDAO;

    public AddressDAOTest() throws SQLException {}

    @BeforeAll
    public static void setupDatabase() throws Exception {
        addressDAO = new AddressDAO();

        // Créer la table accounts (si elle n’existe pas déjà)
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS accounts (
                    id INT PRIMARY KEY AUTO_INCREMENT
                )
            """);

            stmt.execute("INSERT INTO accounts (id) VALUES (1) ON DUPLICATE KEY UPDATE id = id");

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS addresses (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    account_id INT,
                    first_name VARCHAR(50),
                    last_name VARCHAR(50),
                    phone VARCHAR(20),
                    street VARCHAR(100),
                    city VARCHAR(50),
                    postal_code VARCHAR(10),
                    FOREIGN KEY (account_id) REFERENCES accounts(id)
                )
            """);
        }
    }

    @AfterAll
    public static void closeDatabase() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testCreateAndFindById() throws SQLException {
        Address address = new Address(1, "John", "Doe", "123456789", "123 Main St", "Paris", "75001");
        addressDAO.create(address);

        assertTrue(address.getId() > 0);

        Address found = addressDAO.findById(address.getId());
        assertNotNull(found);
        assertEquals("John", found.getFirstName());
        assertEquals("Doe", found.getLastName());
    }

    @Test
    public void testUpdate() throws SQLException {
        Address address = new Address(1, "Jane", "Smith", "999888777", "456 Elm St", "Lyon", "69000");
        addressDAO.create(address);

        address.setCity("Marseille");
        addressDAO.update(address);

        Address updated = addressDAO.findById(address.getId());
        assertEquals("Marseille", updated.getCity());
    }

    @Test
    public void testDelete() throws SQLException {
        Address address = new Address(1, "Mark", "Lee", "111222333", "789 Oak St", "Nice", "06000");
        addressDAO.create(address);
        int id = address.getId();

        addressDAO.delete(id);
        Address deleted = addressDAO.findById(id);

        assertNull(deleted);
    }

    @Test
    public void testFindByAccountId() throws SQLException {
        Address a1 = new Address(1, "Alice", "Dupont", "123123123", "10 Rue A", "Paris", "75002");
        Address a2 = new Address(1, "Bob", "Martin", "456456456", "20 Rue B", "Paris", "75003");
        addressDAO.create(a1);
        addressDAO.create(a2);

        List<Address> addresses = addressDAO.findByAccountId(1);
        assertTrue(addresses.size() >= 2);
    }
}
