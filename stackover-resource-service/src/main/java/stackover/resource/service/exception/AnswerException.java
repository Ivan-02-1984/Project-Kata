package stackover.resource.service.exception;

import java.io.Serial;

public class AnswerException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7115737071472618624L;

    public AnswerException() {
    }

    public AnswerException(String message) {
        super(message);
    }

    public AnswerException(String s, Exception e) {
        super(s, e);
    }
}
