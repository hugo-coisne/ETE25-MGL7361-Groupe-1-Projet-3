package ca.uqam.mgl7361.lel.gp1.account.exception;

public class InvalidCartException extends Exception {

    public InvalidCartException(String message) {
        super(message);
    }

    public InvalidCartException(String message, Throwable cause) {
        super(message, cause);
    }

}
