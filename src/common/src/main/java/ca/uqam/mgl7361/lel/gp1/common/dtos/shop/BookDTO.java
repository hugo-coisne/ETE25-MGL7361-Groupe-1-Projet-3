package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;

public class BookDTO extends DTO {
    private String title;
    private String description;
    private String isbn;
    private Date publicationDate;
    private double price;
    private int stockQuantity;
    private PublisherDTO publisher;
    private List<CategoryDTO> categories;
    private List<AuthorDTO> authors;

    public BookDTO(String title, String isbn, double price) {
        this.setTitle(title);
        this.setIsbn(isbn);
        this.setPrice(price);
        this.description = "";
        this.publicationDate = null;
        this.stockQuantity = 0;
        this.categories = new ArrayList<>();
        this.authors = new ArrayList<>();
    }

    public BookDTO() {
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
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setPublisher(PublisherDTO publisher) {
        this.publisher = publisher;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public void setAuthors(List<AuthorDTO> authors) {
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

    public PublisherDTO getPublisher() {
        return publisher;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

}
