package account.middleware;

import account.exception.DuplicateEmailException;
import account.exception.InvalidCredentialsException;
import common.RunnableWithException;

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
