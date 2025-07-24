package ca.uqam.mgl7361.lel.gp1.common.dtos.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;

public class CartDTO extends DTO {
    private Map<String, Integer> booksIsbn;
    private int userId;
    private List<CartItemDTO> bookDtos;
    private double totalPrice;
    private int id;

    public CartDTO(int User) {
        this.userId = User;
        this.booksIsbn = new HashMap<>();
        this.bookDtos = new ArrayList<CartItemDTO>();
    }

    public CartDTO() {
        this.bookDtos = new ArrayList<CartItemDTO>();
    }

    public void add(BookDTO bookDto, int quantity) {
        if (bookDto != null && quantity > 0) {
            String isbn = bookDto.getIsbn();
            booksIsbn.merge(isbn, quantity, Integer::sum);
        }
    }

    public void setBooks(List<CartItemDTO> books) {
        this.bookDtos = books;
        // update the booksIsbn list
        this.booksIsbn = new HashMap<>();
        if (books != null) {
            for (CartItemDTO entry : books) {
                BookDTO book = entry.book();
                Integer quantity = entry.quantity();
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
    
    public void setId(int id) {
        this.id = id;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItemDTO> getBooksDto() {
        return this.bookDtos;
    }
}
