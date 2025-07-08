package ca.uqam.mgl7361.lel.gp1.lel.gp1.account.presentation;

import java.util.logging.Logger;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.exception.DuplicateEmailException;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.exception.InvalidCredentialsException;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.business.AccountService;

public class AccountAPIImpl implements AccountAPI {

    Logger logger = Logger.getLogger(AccountAPIImpl.class.getName());

    AccountService accountService = AccountService.getInstance();

    @Override
    public AccountDTO signin(String email, String password) {
        logger.info("Attempting to sign in with email: " + email);
        try {
            AccountDTO accountDto = accountService.signin(email, password);
//            System.out.println("Connexion réussie.");
//            System.out.println("Bienvenue " + accountDto.getFirstName() + " " + accountDto.getLastName() + " !");
            return accountDto;
        } catch (InvalidCredentialsException e) {
            logger.warning("Sign in failed due to invalid arguments: " + e.getMessage());
            System.out.println(
                    "Identifiants incorrects. Veuillez vérifier les informations saisies.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error during sign in: " + e.getMessage());
            System.out.println("Une erreur est survenue lors de la connexion. Veuillez réessayer plus tard.");
        }
        return null;
    }

    @Override
    public void signup(String firstName, String lastName, String phone, String email, String password) {
        logger.info("Creating account for: " + firstName + " " + lastName + " with email: " + email);
        try {
            accountService.create(new AccountDTO(firstName, lastName, phone, email, password));
            System.out.println("Compte créé avec succès. Vous pouvez maintenant vous connecter.");
        } catch (DuplicateEmailException e) {
            logger.warning("AccountDTO not created because " + e.getMessage() + " is already in database.");
            System.out.println("Le courriel " + e.getMessage()
                    + " est déjà utilisé. S'il s'agit bien de votre email, veuillez vous connecter."); // see if
                                                                                                       // password reset
                                                                                                       // is needed
        } catch (IllegalArgumentException e) {
            logger.warning("AccountDTO not created because " + e.getMessage());
            System.out.println("Compte non créé. Veuillez vérifier les informations saisies. " + e.getMessage());
        }

        catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error creating account: " + e.getMessage());
        }

    }

    @Override
    public void delete(AccountDTO account) {
        logger.info("Attempting to delete account with email: " + account.getEmail());
        try {
            accountService.delete(account);
            System.out.println("Compte supprimé avec succès.");
        } catch (InvalidCredentialsException e) {
            logger.warning("Deletion failed due to invalid credentials: " + e.getMessage());
            System.out.println("Identifiants incorrects. Veuillez vérifier les informations saisies.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error during account deletion: " + e.getMessage());
            System.out
                    .println("Une erreur est survenue lors de la suppression du compte. Veuillez réessayer plus tard.");
        }
    }

    @Override
    public void changePasswordFor(AccountDTO account, String newPassword) {
        logger.info("Changing password for account with email: " + account.getEmail());
        try {
            accountService.update(account, "password", newPassword);
        } catch (InvalidCredentialsException e) {
            logger.warning("Changing password failed due to invalid credentials: " + e.getMessage());
            System.out.println("Identifiants incorrects. Veuillez vérifier les informations saisies.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error changing password: " + e.getMessage());
            System.out.println(
                    "Une erreur est survenue lors du changement de mot de passe. Veuillez réessayer plus tard.");
        }
    }

    @Override
    public void changePhoneFor(AccountDTO account, String newPhone) {
        logger.info("Changing phone number for account with email: " + account.getEmail());
        try {
            accountService.update(account, "phone", newPhone);
        } catch (InvalidCredentialsException e) {
            logger.warning("Changing phone failed due to invalid credentials: " + e.getMessage());
            System.out.println("Identifiants incorrects. Veuillez vérifier les informations saisies.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error changing phone: " + e.getMessage());
            System.out.println(
                    "Une erreur est survenue lors du changement de numéro de téléphone. Veuillez réessayer plus tard.");
        }
    }

    @Override
    public void changeEmailFor(AccountDTO account, String newEmail) {
        try {
            accountService.update(account, "email", newEmail);
            logger.info("Changing email for account with email: " + account.getEmail());
        } catch (InvalidCredentialsException e) {
            logger.warning("Changing email failed due to invalid credentials: " + e.getMessage());
            System.out.println("Identifiants incorrects. Veuillez vérifier les informations saisies.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error changing email: " + e.getMessage());
            System.out.println(
                    "Une erreur est survenue lors du changement de courriel. Veuillez réessayer plus tard.");
        }
    }

    @Override
    public void changeFirstNameFor(AccountDTO account, String newFirstName) {
        try {
            accountService.update(account, "first_name", newFirstName);
            logger.info("Changing first name for account with email: " + account.getEmail());

        } catch (InvalidCredentialsException e) {
            logger.warning("Changing first name failed due to invalid credentials: " + e.getMessage());
            System.out.println("Identifiants incorrects. Veuillez vérifier les informations saisies.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error changing first name: " + e.getMessage());
            System.out.println(
                    "Une erreur est survenue lors du changement de prénom. Veuillez réessayer plus tard.");
        }
    }

    @Override
    public void changeLastNameFor(AccountDTO account, String newLastName) {
        logger.info("Changing last name for account with email: " + account.getEmail());
        try {
            accountService.update(account, "last_name", newLastName);
        } catch (InvalidCredentialsException e) {
            logger.warning("Changing last name failed due to invalid credentials: " + e.getMessage());
            System.out.println("Identifiants incorrects. Veuillez vérifier les informations saisies.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error changing last name: " + e.getMessage());
            System.out.println(
                    "Une erreur est survenue lors du changement de nom de famille. Veuillez réessayer plus tard.");
        }
    }
}
