package shop.dto;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class BookDTO {
    private String title;
    private String description;
    private String isbn;
    private Date publicationDate;
    private double price;
    private int stockQuantity;
    private PublisherDTO publisher;
    private List<AuthorDTO> authors;

    public BookDTO(String title, String isbn, double price) {
        this.setTitle(title);
        this.setIsbn(isbn);
        this.setPrice(price);
        this.description = "";
        this.publicationDate = null;
        this.stockQuantity = 0;
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
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setPublisher(PublisherDTO publisher) {
        this.publisher = publisher;
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

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

}
