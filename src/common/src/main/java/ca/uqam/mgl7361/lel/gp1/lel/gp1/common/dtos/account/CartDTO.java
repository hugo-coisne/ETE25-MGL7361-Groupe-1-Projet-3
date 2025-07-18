package ca.uqam.mgl7361.lel.gp1.lel.gp1.common.dtos.account;

import java.util.HashMap;
import java.util.Map;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.dtos.shop.BookDTO;

public class CartDTO {
    private Map<String, Integer> booksIsbn;
    private int userId;
    private Map<BookDTO, Integer> bookDtos;
    private double totalPrice;
    private int id;

    public CartDTO(int User) {
        this.userId = User;
        this.booksIsbn = new HashMap<>();
        this.bookDtos = new HashMap<BookDTO, Integer>();
    }

    public CartDTO() {
        this.bookDtos = new HashMap<BookDTO, Integer>();
    }

    public void add(BookDTO bookDto, int quantity) {
        if (bookDto != null && quantity > 0) {
            String isbn = bookDto.getIsbn();
            booksIsbn.merge(isbn, quantity, Integer::sum);
        }
    }

    public void setBooks(Map<BookDTO, Integer> books) {
        this.bookDtos = books;
        // update the booksIsbn list
        this.booksIsbn = new HashMap<>();
        if (books != null) {
            for (Map.Entry<BookDTO, Integer> entry : books.entrySet()) {
                BookDTO book = entry.getKey();
                Integer quantity = entry.getValue();
                if (book != null && book.getIsbn() != null && quantity != null && quantity > 0) {
                    this.booksIsbn.merge(book.getIsbn(), quantity, Integer::sum);
                }
            }
        }
    }

    // SETTERS ----------------------------------------------------------------
    public void setBookIsbn(Map<String, Integer> booksIsbn) {
        this.booksIsbn = booksIsbn;
    }

    public void addBookIsbn(Map<String, Integer> isbnMap) {
        if (isbnMap != null) {
            for (Map.Entry<String, Integer> entry : isbnMap.entrySet()) {
                String isbn = entry.getKey();
                int quantityToAdd = entry.getValue();

                booksIsbn.merge(isbn, quantityToAdd, Integer::sum);
            }
        }
    }

    public void removeBookIsbn(Map<String, Integer> isbnMap) {
        if (isbnMap != null) {
            for (Map.Entry<String, Integer> entry : isbnMap.entrySet()) {
                String isbn = entry.getKey();
                int quantityToRemove = entry.getValue();

                booksIsbn.computeIfPresent(isbn, (k, v) -> {
                    int newQuantity = v - quantityToRemove;
                    return newQuantity > 0 ? newQuantity : null; // Delete entry if quantity is zero or less
                });
            }
        }
    }

    // GETTERS ----------------------------------------------------------------
    public Map<String, Integer> getBookIsbn() {
        return booksIsbn;
    }

    public int getUserId() {
        return userId;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        String s = "CartDTO(id=" + id + ", totalPrice=" + totalPrice + ", bookDtos=[";
        if (this.bookDtos.size() > 0) {
            for (Map.Entry<BookDTO, Integer> entry : bookDtos.entrySet()) {
                BookDTO bookDto = entry.getKey();
                int quantity = entry.getValue();
                s = s + "\nBookDTO(title=" + bookDto.getTitle() + ", isbn=" + bookDto.getIsbn() + ", quantity=" + quantity + ")";
            }
        }
        s = s + "])";
        return s;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<BookDTO, Integer>  getBooksDto() {
        return this.bookDtos;
    }
}
