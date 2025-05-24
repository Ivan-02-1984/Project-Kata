package stackover.resource.service.exception;

import java.io.Serial;

public class CheckedQuestionException extends Exception {  // Changed to checked exception

    @Serial
    private static final long serialVersionUID = 5928912919297163881L;

    public CheckedQuestionException() {
    }

    public CheckedQuestionException(String message) {
        super(message);
    }

    public CheckedQuestionException(String message, Exception cause) {
        super(message, cause);
    }
}