package ca.uqam.mgl7361.lel.gp1.lel.gp1.account.middleware;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.exception.DuplicateEmailException;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.exception.InvalidCredentialsException;

import java.util.logging.Logger;

public class AccountSafeExecutor {
    private static final Logger logger = Logger.getLogger(AccountSafeExecutor.class.getName());

    public static void setup() {
        // Can be used to initialize resources or configurations if needed
    }

    public static void teardown(Exception e) {
        if (e instanceof DuplicateEmailException || e instanceof InvalidCredentialsException) {
            logger.severe(e.getMessage());
        }
    }
}
