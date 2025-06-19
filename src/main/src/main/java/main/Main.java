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
        String email = "john.doe2@mail.com";
        String password = "P@ssword123";
        String wrongPassword = "Password123";
        accountAPI.signup(firstName, lastName, phone, email, password);
        accountAPI.signin(email,  wrongPassword);
        Account account = accountAPI.signin(email,  password);
    }
}
