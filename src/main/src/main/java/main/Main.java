package main;

import account.business.entities.Account;
import account.presentation.AccountAPI;
import account.presentation.AccountAPIImpl;

public class Main {
    public static void main(String[] args) {
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

        // Delete the account
        accountAPI.deleteAccount(email, password);
        System.out.println("");

        // Try to sign in again to confirm deletion
        accountAPI.signin(email, password);
        System.out.println("");

    }
}
