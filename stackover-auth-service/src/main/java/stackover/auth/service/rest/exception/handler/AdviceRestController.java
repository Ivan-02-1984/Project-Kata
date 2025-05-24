package stackover.auth.service.rest.exception.handler;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import stackover.auth.service.dto.response.ErrorResponse;
import stackover.auth.service.exception.*;

import java.time.Instant;


@RestControllerAdvice
@Slf4j
public class AdviceRestController {

    // 1. Обработка кастомных исключений аутентификации
    @ExceptionHandler(AccountExistException.class)
    public ResponseEntity<ErrorResponse> handleAccountExist(AccountExistException ex) {
        log.warn("Account conflict: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, "ACCOUNT_EXISTS", ex);
    }

    @ExceptionHandler(AccountNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotAvailable(AccountNotAvailableException ex) {
        log.warn("Account not available: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.FORBIDDEN, "ACCOUNT_DISABLED", ex);
    }

    // 2. Обработка исключений Feign (вызовов других сервисов)
    @ExceptionHandler(FeignRequestException.class)
    public ResponseEntity<ErrorResponse> handleFeignRequestException(FeignRequestException ex) {
        log.error("Feign client error: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_GATEWAY, "SERVICE_UNAVAILABLE", ex);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException ex) {
        log.error("Feign communication error: {}", ex.getMessage());
        return buildErrorResponse(
                HttpStatus.valueOf(ex.status()),
                "REMOTE_SERVICE_ERROR",
                ex.contentUTF8()
        );
    }

    // 3. Обработка EntityNotFound (для поиска сущностей)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        log.warn("Entity not found: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, "ENTITY_NOT_FOUND", ex);
    }

    // 4. Общая обработка непредвиденных исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        log.error("Internal server error: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "Internal server error"
        );
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJwt(InvalidJwtException ex) {
        log.warn("JWT validation failed: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                        "INVALID_JWT",
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    // Вспомогательный метод для построения ответа
    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String errorCode,
            Exception ex
    ) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(
                        errorCode,
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String errorCode,
            String message
    ) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(
                        errorCode,
                        message,
                        Instant.now()
                ));
    }

}
