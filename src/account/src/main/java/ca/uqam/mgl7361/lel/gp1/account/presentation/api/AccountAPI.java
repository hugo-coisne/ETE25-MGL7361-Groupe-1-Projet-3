package ca.uqam.mgl7361.lel.gp1.account.presentation.api;

import ca.uqam.mgl7361.lel.gp1.account.dto.AccountDTO;

public interface AccountAPI {
    AccountDTO signin(String email, String password) throws Exception;

    void signup(String firstName, String lastName, String phone, String email, String password) throws Exception;

    void changePhoneFor(AccountDTO account, String newPhone);

    void changeEmailFor(AccountDTO account, String newEmail);

    void changeFirstNameFor(AccountDTO account, String newFirstName);

    void changeLastNameFor(AccountDTO account, String newLastName);

    void changePasswordFor(AccountDTO account, String newPassword);

    void delete(AccountDTO account);
}
