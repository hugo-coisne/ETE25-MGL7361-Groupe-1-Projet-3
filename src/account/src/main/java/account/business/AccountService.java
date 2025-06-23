package account.business;

import java.util.logging.Logger;

import account.model.Account;
import account.exception.DuplicateEmailException;
import account.exception.InvalidCredentialsException;
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

    public void delete(Account account) throws InvalidCredentialsException {
        // Logic to delete an account
        logger.info("Deleting account with email: " + account.getEmail());
        logger.info("First signing in with email: " + account.getEmail());
        Account authenticatedAccount = signin(account.getEmail(), account.getPassword());
        logger.info("Deleting account with email " + authenticatedAccount.getEmail());
        accountDao.deleteAccountWithId(authenticatedAccount.getId());
        logger.info("Account with email " + authenticatedAccount.getEmail() + " deleted successfully.");
    }

    public void update(Account account, String parameterToBeUpdated, String newValue)
            throws InvalidCredentialsException {
        // Logic to update an existing account
        logger.info("Updating account with email: " + account.getEmail() + ", parameter: " + parameterToBeUpdated
                + ", new value: " + newValue + ", old value: " + account.getPassword());
        Account authenticatedAccount = signin(account.getEmail(), account.getPassword());
        logger.info("Authenticated account: " + authenticatedAccount.toString());
        switch (parameterToBeUpdated.toLowerCase()) {
            case "phone":
                authenticatedAccount.setPhone(newValue);
                break;
            case "email":
                authenticatedAccount.setEmail(newValue);
                break;
            case "first_name":
                authenticatedAccount.setFirstName(newValue);
                break;
            case "last_name":
                authenticatedAccount.setLastName(newValue);
                break;
            case "password":
                authenticatedAccount.setPassword(newValue);
                break;
            default:
                throw new IllegalArgumentException("Invalid parameter to update: " + parameterToBeUpdated);
        }
        accountDao.update(authenticatedAccount);
        logger.info("Account updated successfully.");
    }
}
