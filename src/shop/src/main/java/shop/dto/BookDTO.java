package shop.dto;

public class BookDTO {
    private final String title;
    private final String author;
    private final String isbn;
    private final double price;

    public BookDTO(String title, String author, String isbn, double price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }

    // GETTERS ----------------------------------------------------------------
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getPrice() {
        return price;
    }
}
