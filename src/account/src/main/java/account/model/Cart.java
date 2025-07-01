package account.model;

import java.util.Map;

import account.dto.CartDTO;
import shop.dto.BookDTO;

public class Cart {

    private Map<BookDTO, Integer> booksDto;
    private int id;
    private double totalPrice;

    public Cart() {
        // Initialize the cart with an empty map
        this.booksDto = new java.util.HashMap<>();
    }

    public Cart(int id, double totalPrice) {
        // Initialize the cart with an empty map
        this.booksDto = new java.util.HashMap<>();
        this.id = id;
        this.totalPrice = totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getQuantity(BookDTO bookDto) {
        return booksDto.getOrDefault(bookDto, 0);
    }

    public CartDTO toDTO() {
        CartDTO cartDTO = new CartDTO();
        Map<BookDTO, Integer> bookDtos = new java.util.HashMap<>();
        cartDTO.setBooks(bookDtos);
        return cartDTO;
    }

    public void setBooksDto(Map<BookDTO, Integer> booksDto) {
        this.booksDto = booksDto;
    }

    public Map<BookDTO, Integer> getBooks() {
        return booksDto;
    }

    public void addBook(BookDTO bookDto, int quantity) {
        if (bookDto != null && quantity > 0) {
            booksDto.merge(bookDto, quantity, Integer::sum);
        }
    }

    public String toString() {
        String s = "Cart (id=" + id + ", totalPrice=" + totalPrice + ", booksDto=[";
        if (this.booksDto.size() > 0) {
            for (Map.Entry<BookDTO, Integer> entry : booksDto.entrySet()) {
                BookDTO bookDto = entry.getKey();
                int quantity = entry.getValue();
                s = s + "\nBookDTO(title=" + bookDto.getTitle() + ", isbn=" + bookDto.getIsbn() + ", quantity="
                        + quantity + ")";
            }
        }
        s = s + "])";
        return s;
    }

}
