package account.business;

import java.util.logging.Logger;

import account.model.Account;
import account.dto.AccountDTO;
import account.exception.DuplicateEmailException;
import account.exception.InvalidCredentialsException;
import account.persistence.AccountDAO;
import account.presentation.CartAPI;
import account.presentation.CartAPIImpl;

public class AccountService {

    static AccountService instance = null;

    Logger logger = Logger.getLogger(AccountService.class.getName());

    AccountDAO accountDao = AccountDAO.getInstance();
    CartAPI cartAPI = new CartAPIImpl();

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    public void create(AccountDTO accountDto) throws DuplicateEmailException, IllegalArgumentException {

        Account account = accountDto.toAccount();
        logger.info("Checking values for " + account.toString());

        ArgumentValidator.checkAccountSignupArguments(account);

        logger.info("Values are valid, inserting " + account.toString() + " in database.");
        accountDao.insert(account);

    }

    public AccountDTO signin(String email, String password) throws InvalidCredentialsException {
        logger.info("Signing in with email: " + email);
        AccountDTO accountDto = accountDao.findByEmailAndPassword(email, password).toDto();
        return accountDto;
    }

    public void delete(AccountDTO accountDto) throws InvalidCredentialsException {
        // Logic to delete an account
        Account account = accountDto.toAccount();
        logger.info("Deleting account with email: " + account.getEmail());
        logger.info("First signing in with email: " + account.getEmail());
        Account authenticatedAccount = signin(account.getEmail(), account.getPassword()).toAccount();
        logger.info("Deleting cart for account with email: " + authenticatedAccount.getEmail());
        cartAPI.clearCart(authenticatedAccount.toDto());
        logger.info("Deleting account with email " + authenticatedAccount.getEmail());
        accountDao.deleteAccountWithId(authenticatedAccount.getId());
        logger.info("Account with email " + authenticatedAccount.getEmail() + " deleted successfully.");
    }

    public void update(AccountDTO accountDto, String parameterToBeUpdated, String newValue)
            throws InvalidCredentialsException {
        // Logic to update an existing account
        Account account = accountDto.toAccount();
        logger.info("Updating account with email: " + account.getEmail() + ", parameter: " + parameterToBeUpdated
                + ", new value: " + newValue + ", old value: " + account.getPassword());
        Account authenticatedAccount = signin(account.getEmail(), account.getPassword()).toAccount();
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
