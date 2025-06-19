package account.presentation;

import account.business.entities.Account;

public interface AccountAPI {
    Account signin(String email, String password);

    void signup(String firstName, String lastName, String phone, String email, String password);

    void changePhoneFor(Account account, String newPhone);

    void changeEmailFor(Account account, String newEmail);

    void changeFirstNameFor(Account account, String newFirstName);

    void changeLastNameFor(Account account, String newLastName);

    void changePasswordFor(Account account, String newPassword);

    void delete(Account account);
}
