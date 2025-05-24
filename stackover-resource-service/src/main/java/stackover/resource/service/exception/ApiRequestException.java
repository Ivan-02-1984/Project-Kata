package stackover.resource.service.exception;

import java.io.Serial;

public class ApiRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -2061683015101611282L;

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String s, Exception e) {
        super(s, e);
    }
}
