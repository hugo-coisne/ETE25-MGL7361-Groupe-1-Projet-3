package account.middleware;

import account.exception.DuplicateEmailException;
import account.exception.InvalidCredentialsException;
import common.RunnableWithException;

import java.util.logging.Logger;

public class AccountSafeExecutor {

    private static final Logger logger = Logger.getLogger(AccountSafeExecutor.class.getName());

    public static void run(RunnableWithException runnable) throws Exception {
        try {
            runnable.run();
        } catch (DuplicateEmailException | InvalidCredentialsException e) {
            logger.severe(e.getMessage());
        }
    }
}
