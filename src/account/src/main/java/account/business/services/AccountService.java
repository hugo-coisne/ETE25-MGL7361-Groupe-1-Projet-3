package account.business.services;

import java.util.logging.Logger;

import account.business.entities.Account;
import account.business.exception.DuplicateEmailException;
import account.business.exception.InvalidCredentialsException;
import account.persistence.AccountDAO;

public class AccountService {

    static AccountService instance = null;

    Logger logger = Logger.getLogger(AccountService.class.getName());

    AccountDAO accountDao = AccountDAO.getInstance();

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    public void create(Account account) throws DuplicateEmailException, IllegalArgumentException {

        logger.info("Checking values for " + account.toString());

        ArgumentValidator.checkAccountSignupArguments(account);

        logger.info("Values are valid, inserting " + account.toString() + " in database.");
        accountDao.insert(account);

    }

    public Account signin(String email, String password) throws InvalidCredentialsException {
        logger.info("Signing in with email: " + email);
        Account account = accountDao.findByEmailAndPassword(email, password);
        return account;
    }

    public void updateAccount(String accountId, String firstName, String lastName, String phone, String email) {
        // Logic to update an existing account
        System.out.println("Updating account with ID: " + accountId);
        // Here you would typically update the account in the database or storage
    }

    public void deleteAccount(String accountId) {
        // Logic to delete an account
        System.out.println("Deleting account with ID: " + accountId);
        // Here you would typically remove the account from the database or storage
    }

    public void changePassword(String accountId, String oldPassword, String newPassword) {
        // Logic to change the password of an account
        System.out.println("Changing password for account with ID: " + accountId);
        // Here you would typically update the password in the database or storage
    }
}
