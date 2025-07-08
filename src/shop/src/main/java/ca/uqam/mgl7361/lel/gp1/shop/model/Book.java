package ca.uqam.mgl7361.lel.gp1.shop.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private String description;
    private String isbn;
    private Date publicationDate;
    private double price;
    private int stockQuantity;
    private Publisher publisher;
    private List<Category> categories;
    private List<Author> authors;

    public Book(String title, String isbn, double price) {
        this.setTitle(title);
        this.setIsbn(isbn);
        this.setPrice(price);
        this.description = "";
        this.publicationDate = null;
        this.stockQuantity = 0;
        this.publisher = null;
        this.categories = new ArrayList<>();
        this.authors = new ArrayList<>();
    }

    // SETTERS ----------------------------------------------------------------
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.stockQuantity = stockQuantity;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setCategories(List<Category> categories) {
        if (categories == null) {
            throw new IllegalArgumentException("Categories cannot be null");
        }
        this.categories = categories;
    }

    public void setAuthors(List<Author> authors) {
        if (authors == null) {
            throw new IllegalArgumentException("Authors cannot be null");
        }
        this.authors = authors;
    }

    // GETTERS ----------------------------------------------------------------
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getIsbn() {
        return isbn;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return title + " (" + isbn + ")";
    }
}
