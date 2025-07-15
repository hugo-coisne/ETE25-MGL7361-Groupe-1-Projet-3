package ca.uqam.mgl7361.lel.gp1.account.presentation.api;

import ca.uqam.mgl7361.lel.gp1.account.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.account.exception.InvalidCredentialsException;

public interface AccountAPI {
    AccountDTO signin(String email, String password) throws Exception;

    void signup(String firstName, String lastName, String phone, String email, String password) throws Exception;

    void changePropertyFor(AccountDTO account, String property, String newValue) throws Exception;

    void delete(AccountDTO account) throws InvalidCredentialsException;
}
