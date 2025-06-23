package main;

import account.middleware.AccountSafeExecutor;
import common.RunnableWithException;
import shop.middleware.ShopSafeExecutor;

public class GlobalSafeExecutor {
    public static void run(RunnableWithException runnable) {
        try {
            ShopSafeExecutor.run(runnable::run);
            AccountSafeExecutor.run(runnable::run);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
