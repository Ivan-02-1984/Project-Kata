package stackover.auth.service.exception;

public class AccountNotAvailableException extends RuntimeException {
    public AccountNotAvailableException() {
        super();
    }

    public AccountNotAvailableException(String message) {
        super(message);
    }
}
