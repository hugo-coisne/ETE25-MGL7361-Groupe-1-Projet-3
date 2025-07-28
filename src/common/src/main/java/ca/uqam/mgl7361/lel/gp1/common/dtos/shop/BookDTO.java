package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;

@Schema(description = "Data Transfer Object representing a book")
public class BookDTO extends DTO {

    @Schema(description = "Title of the book", example = "Clean Code")
    private String title;

    @Schema(description = "Short description or summary of the book", example = "A handbook of agile software craftsmanship")
    private String description;

    @Schema(description = "International Standard Book Number", example = "9780132350884")
    private String isbn;

    @Schema(description = "Date of publication", example = "2008-08-01")
    private Date publicationDate;

    @Schema(description = "Price of the book", example = "49.99")
    private double price;

    @Schema(description = "Number of books available in stock", example = "12")
    private int stockQuantity;

    @Schema(description = "Publisher of the book")
    private PublisherDTO publisher;

    @Schema(description = "List of categories for the book")
    private List<CategoryDTO> categories;

    @Schema(description = "List of authors of the book")
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
