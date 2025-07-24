package ca.uqam.mgl7361.lel.gp1.user.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.user.exception.DuplicateEmailException;
import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidArgumentException;
import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidCartException;
import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidCredentialsException;
import ca.uqam.mgl7361.lel.gp1.user.model.Account;
import ca.uqam.mgl7361.lel.gp1.user.persistence.AccountDAO;
import ca.uqam.mgl7361.lel.gp1.user.mapper.AccountMapper;

@Service
public class AccountService {

    static AccountService instance = null;

    Logger logger = LogManager.getLogger(AccountService.class);

    AccountDAO accountDao = AccountDAO.getInstance();
    static CartService cartService = CartService.getInstance();

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    public void create(AccountDTO accountDto) throws DuplicateEmailException, IllegalArgumentException {

        Account account = AccountMapper.toModel(accountDto);
        logger.debug("Checking values for " + account.toString());

        ArgumentValidator.checkAccountSignupArguments(account);

        logger.debug("Values are valid, inserting " + account.toString() + " in database.");
        accountDao.insert(account);

    }

    public AccountDTO signin(String email, String password) throws InvalidCredentialsException {
        logger.debug("Signing in with email: " + email);
        AccountDTO accountDto = AccountMapper.toDTO(accountDao.findByEmailAndPassword(email, password));
        return accountDto;
    }

    public void delete(AccountDTO accountDto) throws InvalidCredentialsException {
        // Logic to delete an account
        Account account = AccountMapper.toModel(accountDto);
        logger.debug("Deleting account with email: " + account.getEmail());
        logger.debug("First signing in with email: " + account.getEmail());
        Account authenticatedAccount = AccountMapper.toModel(signin(account.getEmail(), account.getPassword()));
        logger.debug("Deleting cart for account with email: " + authenticatedAccount.getEmail());
        try {
            cartService.clearCart(AccountMapper.toDTO(authenticatedAccount));
        } catch (InvalidCartException e) {
            logger.warn("No cart to delete for account: " + authenticatedAccount.getEmail());
        }
        logger.debug("Deleting account with email " + authenticatedAccount.getEmail());
        accountDao.deleteAccountWithId(authenticatedAccount.getId());
        logger.debug("Account with email " + authenticatedAccount.getEmail() + " deleted successfully.");
    }

    public void update(AccountDTO accountDto, String parameterToBeUpdated, String newValue)
            throws InvalidCredentialsException, IllegalArgumentException, DuplicateEmailException {
        // Logic to update an existing account
        Account account = AccountMapper.toModel(accountDto);
        logger.debug("Updating account with email: " + account.getEmail() + ", parameter: " + parameterToBeUpdated
                + ", new value: " + newValue);
        Account authenticatedAccount = AccountMapper.toModel(signin(account.getEmail(), account.getPassword()));
        logger.debug("Authenticated account: " + authenticatedAccount.toString());
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
            logger.warn("Invalid argument: " + parameterToBeUpdated + ", problems: " + problems);
            Map<String, List<String>> problemsMap = Map.of(parameterToBeUpdated, problems);
            throw new InvalidArgumentException(problemsMap);
        }
        accountDao.update(authenticatedAccount);
        logger.debug("Account updated successfully.");
    }
}
