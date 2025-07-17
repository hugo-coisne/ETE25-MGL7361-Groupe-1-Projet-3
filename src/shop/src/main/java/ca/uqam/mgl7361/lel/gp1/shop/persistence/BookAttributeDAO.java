package ca.uqam.mgl7361.lel.gp1.shop.persistence;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.DBConnection;
import ca.uqam.mgl7361.lel.gp1.shop.model.BookAttribute;
import ca.uqam.mgl7361.lel.gp1.shop.exception.AttributesException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.AuthorsException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.CategoriesException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.PublishersException;
import ca.uqam.mgl7361.lel.gp1.shop.model.Author;
import ca.uqam.mgl7361.lel.gp1.shop.model.Category;
import ca.uqam.mgl7361.lel.gp1.shop.model.Publisher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookAttributeDAO {

    private static final Logger logger = LogManager.getLogger(BookAttributeDAO.class);

    public List<Author> getAuthors() throws AuthorsException {
        logger.info("Fetching authors from database...");
        List<Author> authors = new ArrayList<>();
        String query = "SELECT id, name FROM authors";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                authors.add(new Author(rs.getInt("id"), rs.getString("name")));
            }
            logger.info("Successfully retrieved {} authors", authors.size());

        } catch (SQLException e) {
            logger.error("SQL error while fetching authors", e);
            throw new AuthorsException("Error fetching authors: " + e.getMessage(), e);
        }

        return authors;
    }

    public List<Category> getCategories() throws CategoriesException {
        logger.info("Fetching categories from database...");
        List<Category> categories = new ArrayList<>();
        String query = "SELECT id, name FROM categories";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
            logger.info("Successfully retrieved {} categories", categories.size());

        } catch (SQLException e) {
            logger.error("SQL error while fetching categories", e);
            throw new CategoriesException("Error fetching categories: " + e.getMessage(), e);
        }

        return categories;
    }

    public List<Publisher> getPublishers() throws PublishersException {
        logger.info("Fetching publishers from database...");
        List<Publisher> publishers = new ArrayList<>();
        String query = "SELECT id, name FROM publishers";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                publishers.add(new Publisher(rs.getInt("id"), rs.getString("name")));
            }
            logger.info("Successfully retrieved {} publishers", publishers.size());

        } catch (SQLException e) {
            logger.error("SQL error while fetching publishers", e);
            throw new PublishersException("Error fetching publishers: " + e.getMessage(), e);
        }

        return publishers;
    }

    public void addAttributes(List<BookAttribute> bookAttributes) throws AttributesException {
        logger.info("Adding {} attributes to database", bookAttributes.size());
        try (Connection conn = DBConnection.getConnection()) {
            for (BookAttribute attr : bookAttributes) {
                String table = getTableName(attr);
                String query = "INSERT INTO " + table + " (name) VALUES (?)";
                logger.debug("Executing query: {}", query);

                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, attr.getName());
                    stmt.executeUpdate();
                    logger.info("Inserted '{}' into {}", attr.getName(), table);
                }
            }
        } catch (SQLException e) {
            logger.error("SQL error while adding attributes", e);
            throw new AttributesException("Error adding attributes: " + e.getMessage(), e);
        }
    }

    public void removeAttribute(BookAttribute bookAttribute) throws AttributesException {
        String table = getTableName(bookAttribute);
        String query = "DELETE FROM " + table + " WHERE name = ?";
        logger.info("Removing attribute '{}' from table '{}'", bookAttribute.getName(), table);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bookAttribute.getName());
            int rowsAffected = stmt.executeUpdate();
            logger.info("Removed {} row(s) from {}", rowsAffected, table);

        } catch (SQLException e) {
            logger.error("SQL error while removing attribute", e);
            throw new AttributesException("Error removing attribute: " + e.getMessage(), e);
        }
    }

    private String getTableName(BookAttribute attr) {
        switch (attr.getType()) {
            case AUTHOR:
                return "authors";
            case CATEGORY:
                return "categories";
            case PUBLISHER:
                return "publishers";
            default:
                String msg = "Unknown attribute type: " + attr.getType();
                logger.error(msg);
                throw new IllegalArgumentException(msg);
        }
    }
}
