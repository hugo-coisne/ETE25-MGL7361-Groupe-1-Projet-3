package main;

import account.middleware.AccountSafeExecutor;
import common.RunnableWithException;
import shop.middleware.ShopSafeExecutor;

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
