package account.presentation;

import account.business.entities.Account;

public interface AccountAPI {
    Account signin(String email, String password);

    void signup(String firstName, String lastName, String phone, String email, String password);

    void updateAccount(Account account);

    void changeAccountPassword(Account account, String oldPassword, String newPassword);

    void deleteAccount();
}
