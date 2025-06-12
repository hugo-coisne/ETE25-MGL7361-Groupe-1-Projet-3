

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting main application...");

        System.out.println("Calling Shop...");
        System.out.println("Calling Order...");
        System.out.println("Calling Payment...");
        System.out.println("Calling User...");
        System.out.println("Calling Delivery...");

        logger.info("Application finished.");
    }
}
