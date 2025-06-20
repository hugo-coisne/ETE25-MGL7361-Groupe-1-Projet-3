package account.business.entities;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private String cartId;
    private String accountId;
    private Map<String, Integer> items; // ISBN as key, quantity as value

    public Cart(String cartId, String accountId) {
        this.cartId = cartId;
        this.accountId = accountId;
        this.items = new HashMap<>();
    }

    // Setters
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void addItem(String isbn, int quantity) {
        this.items.put(isbn, this.items.getOrDefault(isbn, 0) + quantity);
    }

    public void removeItem(String isbn, int quantity) {
        /*
         * Removes an item from the cart.
         * If the quantity to remove is greater than or equal to
         * the current quantity, the item is completely removed.
         */
        if (this.items.containsKey(isbn)) {
            int currentQuantity = this.items.get(isbn);
            if (currentQuantity <= quantity) {
                this.items.remove(isbn);
            } else {
                this.items.put(isbn, currentQuantity - quantity);
            }
        }
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }


    // Getters
    public String getCartId() {
        return cartId;
    }

    public String getAccountId() {
        return accountId;
    }

    public Map<String, Integer> getItems() {
        return items;
    }
}
