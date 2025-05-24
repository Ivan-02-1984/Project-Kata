package stackover.auth.service.exception;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        String errorMessage = getErrorMessage(response);

        return switch (response.status()) {
            case 404 -> new EntityNotFoundException("Profile not found: " + errorMessage);
            case 503 -> new FeignRequestException("Service unavailable: " + errorMessage);
            default -> FeignException.errorStatus(methodKey, response);
        };
    }

    private String getErrorMessage(Response response) {
        try {
            if (response.body() != null) {
                return new String(response.body().asInputStream().readAllBytes());
            }
        } catch (IOException e) {
            return "Failed to read error response";
        }
        return "No error details";
    }
}
