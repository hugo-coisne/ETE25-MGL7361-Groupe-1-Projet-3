package main;

import account.presentation.AccountAPI;
import account.presentation.AccountAPIImpl;

public class Main {
    public static void main(String[] args) {
        AccountAPI accountAPI = new AccountAPIImpl();
        accountAPI.signup("John", "Doe", "1234567890", "john.doe@mail.com", "password123");
    }
}
