package shop.middleware;

import common.RunnableWithException;
import shop.exception.DTOException;
import shop.exception.DuplicationBookException;

import java.util.logging.Logger;

public class ShopSafeExecutor {
    private static final Logger logger = Logger.getLogger(ShopSafeExecutor.class.getName());

    public static void setup() {
        // Can be used to initialize resources or configurations if needed
    }

    public static void teardown(Exception e) {
        if (e instanceof DuplicationBookException || e instanceof DTOException) {
            logger.severe(e.getMessage());
        }
    }
}
