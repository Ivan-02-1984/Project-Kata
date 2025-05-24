package stackover.resource.service.exception;

public class CheckedAccountExistsException extends Exception {  // Changed to checked exception

    public CheckedAccountExistsException(String message) {
        super(message);
    }

    public CheckedAccountExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}