package main;

import account.dto.AccountDTO;
import account.dto.CartDTO;
import account.presentation.AccountAPI;
import account.presentation.AccountAPIImpl;
import order.presentation.OrderAPIImpl;
import shop.dto.BookDTO;
import shop.dto.BookProperty;
import shop.presentation.BookAPIImpl;

import java.util.Map;
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

    public static void shop() throws Exception {
        BookAPIImpl bookAPI = new BookAPIImpl();

        BookDTO bookDTO = new BookDTO("My Book", "1234567890", 19.99);
//        bookAPI.createBook(bookDTO);
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
                        BookProperty.AUTHOR, "%Victor%"
                )
        );
        System.out.println("Books found: " + books.size());
    }

    public static void order() throws Exception {
        OrderAPIImpl orderAPI = new OrderAPIImpl();
        AccountDTO accountDTO = new AccountDTO(
                "John",
                "Doe",
                "1234567890",
                "my@email.com"
        );
        accountDTO.setId(1); // Assuming the account with ID 1 exists
        CartDTO cartDTO = new CartDTO(1);
        cartDTO.addBookIsbn(
                Map.of(
                        "9782070409188t", 2, // Les Misérables
                        "9782070360021", 1 // The Stranger
                )
        );
        orderAPI.createOrder(accountDTO, cartDTO);
    }

    public static void main(String[] args) {
        GlobalSafeExecutor.run(() -> {
            Main.account();
//            Main.shop();
//            Main.order();
        });
    }
}
