package main;

import account.business.entities.Account;
import account.presentation.AccountAPI;
import account.presentation.AccountAPIImpl;
import shop.business.BookService;
import shop.dto.BookDTO;
import shop.persistence.BookDAO;
import shop.presentation.BookAPIImpl;

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
        Account account = accountAPI.signin(email, password);
        System.out.println("");

        // Change password
        String newPassword = "NewP@ssword123";
        accountAPI.changePasswordFor(account, newPassword);
        System.out.println("");

        // Try to sign in with the new password
        Account account2 = accountAPI.signin(email, newPassword);
        System.out.println("");

        // Change phone number
        String newPhone = "0987654321";
        accountAPI.changePhoneFor(account2, newPhone);
        System.out.println("");

        // Try to sign in with the new password with new phone number
        Account account3 = accountAPI.signin(email, newPassword);
        System.out.println("");

        // Change first name
        String newFirstName = "Jane";
        accountAPI.changeFirstNameFor(account3, newFirstName);
        System.out.println("");

        // Try to sign in with the new password with new first name
        Account account4 = accountAPI.signin(email, newPassword);
        System.out.println("");

        // Change last name
        String newLastName = "Smith";
        accountAPI.changeLastNameFor(account4, newLastName);
        System.out.println("");

        // Try to sign in with the new password with new last name
        Account account5 = accountAPI.signin(email, newPassword);
        System.out.println("");

        // Change email
        String newEmail = "jane.smith@mail.com";
        accountAPI.changeEmailFor(account5, newEmail);
        System.out.println("");

        // Try to sign in with the new password with new email
        Account account6 = accountAPI.signin(newEmail, newPassword);
        System.out.println("");

        // Delete the account
        accountAPI.delete(account6);
        System.out.println("");

        // Try to sign in again to confirm deletion
        accountAPI.signin(newEmail, newPassword);
        System.out.println("");
    }

    public static void shop() throws Exception{
        BookDAO bookDAO = new BookDAO();
        BookService bookService = new BookService(bookDAO);
        BookAPIImpl bookAPI = new BookAPIImpl(bookService);

        BookDTO bookDTO = new BookDTO("My Book", "John Smith", "1234567890", 19.99);
        bookAPI.createBook(bookDTO);
    }

    public static void main(String[] args) {
        GlobalSafeExecutor.run(() -> {
            Main.account();
            Main.shop();
        });
    }
}
