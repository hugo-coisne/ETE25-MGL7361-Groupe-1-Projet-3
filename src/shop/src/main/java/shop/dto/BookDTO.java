package shop.dto;

import java.sql.Timestamp;


public class BookDTO {
    private String title;
    private String description;
    private String isbn;
    private Timestamp publicationDate;
    private double price;
    private int stockQuantity;


    public BookDTO(String title, String isbn, double price) {
        this.setTitle(title);
        this.setIsbn(isbn);
        this.setPrice(price);
        this.description = "";
        this.publicationDate = null;
        this.stockQuantity = 0;
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

    public void setPublicationDate(Timestamp publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
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

    public Timestamp getPublicationDate() {
        return publicationDate;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

}
