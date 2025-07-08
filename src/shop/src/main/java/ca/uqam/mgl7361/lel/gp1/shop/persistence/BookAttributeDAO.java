package ca.uqam.mgl7361.lel.gp1.shop.persistence;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.DBConnection;
import ca.uqam.mgl7361.lel.gp1.shop.model.BookAttribute;
import ca.uqam.mgl7361.lel.gp1.shop.model.Author;
import ca.uqam.mgl7361.lel.gp1.shop.model.Category;
import ca.uqam.mgl7361.lel.gp1.shop.model.Publisher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookAttributeDAO {

    public List<Author> getAuthors() throws SQLException {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT id, name FROM authors";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                authors.add(new Author(rs.getInt("id"), rs.getString("name")));
            }
        }

        return authors;
    }

    public List<Category> getCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT id, name FROM categories";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        }

        return categories;
    }

    public List<Publisher> getPublishers() throws SQLException {
        List<Publisher> publishers = new ArrayList<>();
        String query = "SELECT id, name FROM publishers";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                publishers.add(new Publisher(rs.getInt("id"), rs.getString("name")));
            }
        }

        return publishers;
    }

    public void addAttributes(List<BookAttribute> bookAttributes) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            for (BookAttribute attr : bookAttributes) {
                String table = getTableName(attr);
                String query = "INSERT INTO " + table + " (name) VALUES (?)";

                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, attr.getName());
                    stmt.executeUpdate();
                }
            }
        }
    }

    public void removeAttribute(BookAttribute bookAttribute) throws SQLException {
        String table = getTableName(bookAttribute);
        String query = "DELETE FROM " + table + " WHERE name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, bookAttribute.getName());
            stmt.executeUpdate();
        }
    }

    private String getTableName(BookAttribute attr) {
        switch (attr.getType()) {
            case AUTHOR: return "authors";
            case CATEGORY: return "categories";
            case PUBLISHER: return "publishers";
            default: throw new IllegalArgumentException("Unknown attribute type: " + attr.getType());
        }
    }
}
