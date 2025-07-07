package account.exception;

public class UnsufficientStockException extends Exception {
    public UnsufficientStockException(String message) {
        super(message);
    }

    public UnsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
