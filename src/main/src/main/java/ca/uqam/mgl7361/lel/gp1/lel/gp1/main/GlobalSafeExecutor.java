package ca.uqam.mgl7361.lel.gp1.lel.gp1.main;

import ca.uqam.mgl7361.lel.gp1.account.middleware.AccountSafeExecutor;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.RunnableWithException;
import ca.uqam.mgl7361.lel.gp1.shop.middleware.ShopSafeExecutor;

public class GlobalSafeExecutor {
    public static void run(RunnableWithException runnable) {
        Exception exception = null;

        try {
            ShopSafeExecutor.setup();
            AccountSafeExecutor.setup();

            runnable.run();

        } catch (Exception e) {
            exception = e;
        } finally {
            AccountSafeExecutor.teardown(exception);
            ShopSafeExecutor.teardown(exception);
            if (exception != null) {
                System.err.println("An error occurred: " + exception.getMessage());
            }
        }
    }
}
