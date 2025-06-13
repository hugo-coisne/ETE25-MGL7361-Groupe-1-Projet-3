package account.business.services;

import java.util.logging.Logger;

import account.business.entities.Account;
import account.business.exception.DuplicateEmailException;
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

    // This class will contain methods to handle business logic related to accounts
    // For example, methods to create, update, delete accounts, etc.

    public void create(Account account) throws DuplicateEmailException {
        // Logic to create an account
        logger.info("Creating " + account.toString());
        
        accountDao.insert(account);

    }

    // Other methods like updateAccount, deleteAccount, etc. can be added here
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

    public void getAccount(String email, String password) {
        // Logic to retrieve an account based on email and password
        System.out.println("Retrieving account for email: " + email);
        // Here you would typically fetch the account from the database or storage
    }
}
