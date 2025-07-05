package main;

import account.dto.AccountDTO;
import account.dto.CartDTO;
import account.presentation.AccountAPI;
import account.presentation.AccountAPIImpl;
import account.presentation.CartAPI;
import account.presentation.CartAPIImpl;
import order.presentation.OrderAPIImpl;
import shop.dto.BookDTO;
import shop.dto.BookProperty;
import shop.model.Book;
import shop.presentation.BookAPI;
import shop.presentation.BookAPIImpl;
import shop.presentation.BookAttributeAPI;
import shop.presentation.BookAttributeAPIImpl;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class Main {
        public static void account() {
                AccountAPI accountAPI = new AccountAPIImpl();
                String firstName = "John";
                String lastName = "Doe";
                String phone = "1234567890";
                String email = "john.doe@mail.com";
                String password = "P@ssword123";
                String wrongPassword = "Password123";

                // Try to sign in with an unregistered account
                accountAPI.signin(email, password);
                System.out.println("");

                // Create a new account
                accountAPI.signup(firstName, lastName, phone, email, password);
                System.out.println("");

                // Sign in with the newly created account but with a wrong password
                accountAPI.signin(email, wrongPassword);
                System.out.println("");

                // Sign in with the correct password
                AccountDTO account = accountAPI.signin(email, password);
                System.out.println("");

                // Change password
                String newPassword = "NewP@ssword123";
                accountAPI.changePasswordFor(account, newPassword);
                System.out.println("");

                // Try to sign in with the new password
                AccountDTO account2 = accountAPI.signin(email, newPassword);
                System.out.println("");

                // Change phone number
                String newPhone = "0987654321";
                accountAPI.changePhoneFor(account2, newPhone);
                System.out.println("");

                // Try to sign in with the new password with new phone number
                AccountDTO account3 = accountAPI.signin(email, newPassword);
                System.out.println("");

                // Change first name
                String newFirstName = "Jane";
                accountAPI.changeFirstNameFor(account3, newFirstName);
                System.out.println("");

                // Try to sign in with the new password with new first name
                AccountDTO account4 = accountAPI.signin(email, newPassword);
                System.out.println("");

                // Change last name
                String newLastName = "Smith";
                accountAPI.changeLastNameFor(account4, newLastName);
                System.out.println("");

                // Try to sign in with the new password with new last name
                AccountDTO account5 = accountAPI.signin(email, newPassword);
                System.out.println("");

                // Change email
                String newEmail = "jane.smith@mail.com";
                accountAPI.changeEmailFor(account5, newEmail);
                System.out.println("");

                // Try to sign in with the new password with new email
                AccountDTO account6 = accountAPI.signin(newEmail, newPassword);
                System.out.println("");

                // Delete the account
                accountAPI.delete(account6);
                System.out.println("");

                // Try to sign in again to confirm deletion
                accountAPI.signin(newEmail, newPassword);
                System.out.println("");
        }

        public static void cart() {
                BookAPI bookAPI = new BookAPIImpl();
                AccountAPI accountAPI = new AccountAPIImpl();
                String firstName = "John";
                String lastName = "Doe";
                String phone = "1234567890";
                String email = "john.doe@mail.com";
                String password = "P@ssword123";

                // Create a new account
                accountAPI.signup(firstName, lastName, phone, email, password);
                System.out.println("");
                // Sign in with the correct password
                AccountDTO account = accountAPI.signin(email, password);
                System.out.println("");

                // Get a CartAPI instance for the signed-in account
                CartAPI cartAPI = new CartAPIImpl();

                // Retrieve the cart for the account, creating the cart if it
                // doesn't exist
                cartAPI.getCart(account);
                List<BookDTO> books = new ArrayList<BookDTO>(); // TODO : PLEASE HANDLE EXCEPTIONS CORRECTLY : IN API
                                                                // IMPLEMENTATIONS !
                try {
                        books = bookAPI.getBooksBy(
                                        Map.of(
                                                        BookProperty.TITLE, "%"));
                } catch (Exception e) {
                        e.printStackTrace();
                }

                System.out.println(books.get(0));

                BookDTO bookToRemoveLater = books.get(0); // Get the first book to remove later

                books.forEach(book -> {
                        cartAPI.add(book, account);
                });

                // Retrieve the cart again to see the added book
                System.out.println("");
                System.out.println("");
                cartAPI.getCart(account);
                System.out.println("");
                System.out.println("");

                // Add a book to the cart
                cartAPI.add(bookToRemoveLater, account);
                System.out.println("");
                System.out.println("");
                cartAPI.getCart(account);
                System.out.println("");
                System.out.println("");

                // Remove a book from the cart
                cartAPI.remove(bookToRemoveLater, account);

                // Retrieve the cart again to see the added book
                System.out.println("");
                System.out.println("");
                cartAPI.getCart(account);
                System.out.println("");
                System.out.println("");

                // clear the cart
                cartAPI.clearCart(account);

                // Retrieve the cart again to see that it is empty
                System.out.println("");
                System.out.println("");
                cartAPI.getCart(account);
                System.out.println("");
                System.out.println("");

                // Add multiple books to the cart

                accountAPI.delete(account);

        }

        public static void shop() throws Exception {
                BookAPIImpl bookAPI = new BookAPIImpl();

                BookDTO bookDTO = new BookDTO("My Book", "1234567890", 19.99);
                // bookAPI.createBook(bookDTO);
                List<BookDTO> books = bookAPI.getBooksBy(
                                Map.of(
                                                BookProperty.TITLE, "%Les Misérables%",
                                                BookProperty.DESCRIPTION, "%Hugo%",
                                                BookProperty.ISBN, "%88",
                                                BookProperty.PUBLICATION_DATE, "%1862%",
                                                BookProperty.PRICE, "19.99",
                                                BookProperty.STOCK_QUANTITY, "10",
                                                BookProperty.PUBLISHER, "Gallimard",
                                                BookProperty.CATEGORY, "NOVEL",
                                                BookProperty.AUTHOR, "%Victor%"));
                System.out.println("Books found: " + books.size());
        }

        public static void order() throws Exception {
                OrderAPIImpl orderAPI = new OrderAPIImpl();
                AccountDTO accountDTO = new AccountDTO(
                                "John",
                                "Doe",
                                "1234567890",
                                "my@email.com");
                accountDTO.setId(1); // Assuming the account with ID 1 exists
                CartDTO cartDTO = new CartDTO(1);
                cartDTO.addBookIsbn(
                                Map.of(
                                                "9782070409188t", 2, // Les Misérables
                                                "9782070360021", 1 // The Stranger
                                ));
                orderAPI.createOrder(accountDTO, cartDTO);
        }

        public static void scenario() { // scenario described in the provided specifications
                // signup
                // create an account with first name, last name, phone, email and password
                String firstName = "Alice";
                String lastName = "Smith";
                String phone = "1234567890";
                String email = "alice.smith@mail.com";
                String password = "P@ssword123";
                AccountAPI accountAPI = new AccountAPIImpl();
                accountAPI.signup(firstName, lastName, phone, email, password);
                /* TODO : Should show that signup was a success */

                // signin
                // sign in with the created account
                AccountDTO account = accountAPI.signin(email, password);
                /* DONE : Should show that signin was a success */

                // get the cart for the account
                // if the cart does not exist, it should be created
                CartAPI cartAPI = new CartAPIImpl();
                cartAPI.getCart(account);
                /* Should show that the cart was retrieved successfully */

                // browse books using : author, title, isbn, category (one example for each
                // minimum)
                BookAttributeAPI bookAttributeAPI = new BookAttributeAPIImpl();
                BookAPI bookAPI = new BookAPIImpl();
                bookAttributeAPI.getAuthors();
                /* Should show the authors */

                bookAttributeAPI.getCategories();
                /* Should show the categories */

                List<BookDTO> booksByTitle = new ArrayList<BookDTO>();
                try {
                        booksByTitle = bookAPI.getBooksBy(Map.of(
                                        BookProperty.TITLE, "%")); // get all books
                        /* Should display all books */
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                } // TODO : please handle exceptions correctly (in API implementations) to not
                  // have to catch them here

                try {
                        booksByTitle = bookAPI.getBooksBy(Map.of(
                                        BookProperty.TITLE, "Les Misérables"));
                        /* Should display all books */
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

                BookDTO book = booksByTitle.get(0);

                // add an individual book to the cart and show the cart
                cartAPI.add(book, account);
                cartAPI.getCart(account);
                /* Should show that the book was added to the cart */

                // add multiple books to the cart TODO

                // remove book(s) from cart

                // place the order with payment
                // books in cart should make a new order with a "awaiting delivery" status and
                // be removed from the cart (use clearCart) and stock
                // planned delivery dates should be shown on the invoice

                // check history of orders

                // check order details (invoice) for a specific order, showing especially if a
                // given book was delivered or not

                // deliver, simulating a passage of time, the order should be updated to
                // "delivered" status

                // check order details again, showing especially if a given book was delivered
                // or not after the wait

        }

        public static void main(String[] args) {
                GlobalSafeExecutor.run(() -> {
                        // Main.account();
                        Main.cart();
                        // Main.shop();
                        // Main.order();
                });
        }
}
