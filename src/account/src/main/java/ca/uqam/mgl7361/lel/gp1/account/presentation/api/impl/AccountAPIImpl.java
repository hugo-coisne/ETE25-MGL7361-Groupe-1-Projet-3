package ca.uqam.mgl7361.lel.gp1.account.presentation.api.impl;

import java.util.logging.Logger;

import ca.uqam.mgl7361.lel.gp1.account.business.AccountService;
import ca.uqam.mgl7361.lel.gp1.account.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.account.exception.DuplicateEmailException;
import ca.uqam.mgl7361.lel.gp1.account.exception.InvalidArgumentException;
import ca.uqam.mgl7361.lel.gp1.account.exception.InvalidCredentialsException;
import ca.uqam.mgl7361.lel.gp1.account.presentation.api.AccountAPI;

public class AccountAPIImpl implements AccountAPI {

    Logger logger = Logger.getLogger(AccountAPIImpl.class.getName());

    AccountService accountService = AccountService.getInstance();

    @Override
    public AccountDTO signin(String email, String password) throws InvalidCredentialsException {
        logger.info("Attempting to sign in with email: " + email);
        AccountDTO accountDto = accountService.signin(email, password);
        return accountDto;
    }

    @Override
    public void signup(String firstName, String lastName, String phone, String email, String password)
            throws IllegalArgumentException, DuplicateEmailException {
        logger.info("Creating account for: " + firstName + " " + lastName + " with email: " + email);
        accountService.create(new AccountDTO(firstName, lastName, phone, email, password));
    }

    @Override
    public void changePropertyFor(AccountDTO account, String property, String newValue)
            throws InvalidCredentialsException, IllegalArgumentException, InvalidArgumentException, DuplicateEmailException {
        logger.info("Changing property " + property + " for account with email: " + account.getEmail());
        accountService.update(account, property, newValue);
    }

    @Override
    public void delete(AccountDTO account) throws InvalidCredentialsException {
        logger.info("Attempting to delete account with email: " + account.getEmail());
        accountService.delete(account);
    }
}
