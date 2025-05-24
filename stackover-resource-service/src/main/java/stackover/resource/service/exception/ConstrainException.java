package stackover.resource.service.exception;

import java.io.Serial;

public class ConstrainException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5723952907135446546L;

    public ConstrainException() {
    }

    public ConstrainException(String message) {
        super(message);
    }

    public ConstrainException(String s, Exception e) {
        super(s, e);
    }
}
