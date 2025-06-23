package account.dto;

import java.util.HashMap;
import java.util.Map;


public class CartDTO {
    private Map<String, Integer> booksIsbn;
    private final int userId;

    public CartDTO(int User) {
        this.userId = User;
        this.booksIsbn = new HashMap<>();
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
}
