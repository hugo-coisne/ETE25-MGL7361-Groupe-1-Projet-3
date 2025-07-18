package ca.uqam.mgl7361.lel.gp1.user.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import ca.uqam.mgl7361.lel.gp1.user.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.user.exception.DuplicateEmailException;
import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidArgumentException;
import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidCartException;
import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidCredentialsException;
import ca.uqam.mgl7361.lel.gp1.user.model.Account;
import ca.uqam.mgl7361.lel.gp1.user.persistence.AccountDAO;

@Service
public class AccountService {

    static AccountService instance = null;

    Logger logger = Logger.getLogger(AccountService.class.getName());

    AccountDAO accountDao = AccountDAO.getInstance();
    static CartService cartService = CartService.getInstance();

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
        try {
            cartService.clearCart(authenticatedAccount.toDto());
        } catch (InvalidCartException e) {
            logger.warning("No cart to delete for account: " + authenticatedAccount.getEmail());
        }
        logger.info("Deleting account with email " + authenticatedAccount.getEmail());
        accountDao.deleteAccountWithId(authenticatedAccount.getId());
        logger.info("Account with email " + authenticatedAccount.getEmail() + " deleted successfully.");
    }

    public void update(AccountDTO accountDto, String parameterToBeUpdated, String newValue)
            throws InvalidCredentialsException, IllegalArgumentException, DuplicateEmailException {
        // Logic to update an existing account
        Account account = accountDto.toAccount();
        logger.info("Updating account with email: " + account.getEmail() + ", parameter: " + parameterToBeUpdated
                + ", new value: " + newValue);
        Account authenticatedAccount = signin(account.getEmail(), account.getPassword()).toAccount();
        logger.info("Authenticated account: " + authenticatedAccount.toString());
        List<String> problems = new ArrayList<>();
        switch (parameterToBeUpdated.toLowerCase()) {
            case "phone":
                problems = ArgumentValidator.checkPhone(newValue);
                authenticatedAccount.setPhone(newValue);
                break;
            case "email":
                problems = ArgumentValidator.checkEmail(newValue);
                authenticatedAccount.setEmail(newValue);
                break;
            case "firstname":
                problems = ArgumentValidator.checkName(newValue);
                authenticatedAccount.setFirstName(newValue);
                break;
            case "lastname":
                problems = ArgumentValidator.checkName(newValue);
                authenticatedAccount.setLastName(newValue);
                break;
            case "password":
                problems = ArgumentValidator.checkPassword(newValue);
                authenticatedAccount.setPassword(newValue);
                break;
            default:
                throw new IllegalArgumentException("Invalid property: " + parameterToBeUpdated);
        }
        if (!problems.isEmpty()) {
            logger.warning("Invalid argument: " + parameterToBeUpdated + ", problems: " + problems);
            Map<String, List<String>> problemsMap = Map.of(parameterToBeUpdated, problems);
            throw new InvalidArgumentException(problemsMap);
        }
        accountDao.update(authenticatedAccount);
        logger.info("Account updated successfully.");
    }
}
