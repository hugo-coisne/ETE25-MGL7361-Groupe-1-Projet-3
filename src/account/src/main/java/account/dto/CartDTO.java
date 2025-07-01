package account.dto;

import java.util.HashMap;
import java.util.Map;

import account.model.Cart;
import shop.dto.BookDTO;

public class CartDTO {
    private Map<String, Integer> booksIsbn;
    private int userId;
    private Map<BookDTO, Integer> bookDtos;
    private double totalPrice;
    private int id;

    public CartDTO(int User) {
        this.userId = User;
        this.booksIsbn = new HashMap<>();
    }

    public CartDTO() {
    }

    public void add(BookDTO bookDto, int quantity) {
        if (bookDto != null && quantity > 0) {
            String isbn = bookDto.getIsbn();
            booksIsbn.merge(isbn, quantity, Integer::sum);
        }
    }

    public void setBooks(Map<BookDTO, Integer> books) {
        this.bookDtos = books;
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

    public Cart toCart() {
        Cart cart = new Cart();
        cart.setId(this.id);
        cart.setTotalPrice(this.totalPrice);
        Map<BookDTO, Integer> booksDto = new HashMap<>();
        for (Map.Entry<BookDTO, Integer> entry : bookDtos.entrySet()) {
            BookDTO bookDto = entry.getKey();
            int quantity = entry.getValue();
            booksDto.put(bookDto, quantity);
        }
        cart.setBooksDto(booksDto);
        return cart;
    }
}
