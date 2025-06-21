package shop.middleware;

import common.RunnableWithException;
import shop.exception.DTOException;
import shop.exception.DuplicationBookException;

import java.util.logging.Logger;

public class ShopSafeExecutor {
    private static final Logger logger = Logger.getLogger(ShopSafeExecutor.class.getName());

    public static void run(RunnableWithException runnable) throws Exception {
        try {
            runnable.run();
        } catch (DuplicationBookException | DTOException e) {
            logger.severe(e.getMessage());
        }
    }
}
