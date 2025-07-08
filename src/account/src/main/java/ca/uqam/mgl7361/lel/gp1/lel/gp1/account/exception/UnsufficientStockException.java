package ca.uqam.mgl7361.lel.gp1.lel.gp1.account.exception;

public class UnsufficientStockException extends Exception {
    public UnsufficientStockException(String message) {
        super(message);
    }

    public UnsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
