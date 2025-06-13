package main;

import account.presentation.AccountAPI;
import account.presentation.AccountAPIImpl;

public class Main {
    public static void main(String[] args) {
        AccountAPI accountAPI = new AccountAPIImpl();
        accountAPI.signup("", "", "", "jo@h.ndoe@mail.com", "pass");
    }
}
