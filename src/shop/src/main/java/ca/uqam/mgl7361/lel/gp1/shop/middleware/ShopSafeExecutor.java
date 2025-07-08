package ca.uqam.mgl7361.lel.gp1.shop.middleware;

import ca.uqam.mgl7361.lel.gp1.shop.exception.DTOException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.DuplicationBookException;

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
