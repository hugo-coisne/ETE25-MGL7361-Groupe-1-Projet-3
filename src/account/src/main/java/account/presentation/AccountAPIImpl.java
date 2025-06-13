package account.presentation;

import java.util.logging.Logger;

import account.business.entities.Account;
import account.business.exception.DuplicateEmailException;
import account.business.services.AccountService;

public class AccountAPIImpl implements AccountAPI {

    Logger logger = Logger.getLogger(AccountAPIImpl.class.getName());

    AccountService accountService = AccountService.getInstance();

    @Override
    public Account signin(String email, String password) {
        // TODO: Implementation to retrieve account based on email and password
        return null; // Placeholder return
    }

    @Override
    public void signup(String firstName, String lastName, String phone, String email, String password) {
        logger.info("Creating account for: " + firstName + " " + lastName + " with email: " + email);
        try {
            accountService.create(new Account(firstName, lastName, phone, email, password));
        } catch (DuplicateEmailException e) {
            logger.warning("Account not created because " + e.getMessage() + " is already in database.");
            System.out.println("Email " + e.getMessage() + "déjà utilisé. S'il s'agit bien de votre email, veuillez vous connecter."); // see if password reset is needed
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error creating account: " + e.getMessage());
        }

    }

    @Override
    public void updateAccount(Account account) {
        // TODO: Implementation to update an existing account
    }

    @Override
    public void changeAccountPassword(Account account, String oldPassword, String newPassword) {
        // TODO: Implementation to change the account password
    }

    @Override
    public void deleteAccount() {
        // TODO: Implementation to delete the account
    }
}
