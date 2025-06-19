package account.presentation;

import java.util.logging.Logger;

import account.business.entities.Account;
import account.business.exception.DuplicateEmailException;
import account.business.exception.InvalidCredentialsException;
import account.business.services.AccountService;

public class AccountAPIImpl implements AccountAPI {

    Logger logger = Logger.getLogger(AccountAPIImpl.class.getName());

    AccountService accountService = AccountService.getInstance();

    @Override
    public Account signin(String email, String password) {
        logger.info("Attempting to sign in with email: " + email);
        try {
            Account account = accountService.signin(email, password);
            return account;
        } catch (InvalidCredentialsException e) {
            logger.warning("Sign in failed due to invalid arguments: " + e.getMessage());
            System.out.println(
                    "Identifiants incorrects. Veuillez vérifier les informations saisies.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error during sign in: " + e.getMessage());
            System.out.println("Une erreur est survenue lors de la connexion. Veuillez réessayer plus tard.");
        }
        return null; // Placeholder return
    }

    @Override
    public void signup(String firstName, String lastName, String phone, String email, String password) {
        logger.info("Creating account for: " + firstName + " " + lastName + " with email: " + email);
        try {
            accountService.create(new Account(firstName, lastName, phone, email, password));
        } catch (DuplicateEmailException e) {
            logger.warning("Account not created because " + e.getMessage() + " is already in database.");
            System.out.println("Le courriel " + e.getMessage()
                    + " est déjà utilisé. S'il s'agit bien de votre email, veuillez vous connecter."); // see if
                                                                                                       // password reset
                                                                                                       // is needed
        } catch (IllegalArgumentException e) {
            logger.warning("Account not created because " + e.getMessage());
            System.out.println("Compte non créé. Veuillez vérifier les informations saisies. " + e.getMessage());
        }

        catch (Exception e) {
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
