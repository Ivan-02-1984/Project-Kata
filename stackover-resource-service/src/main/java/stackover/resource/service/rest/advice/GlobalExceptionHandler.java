package stackover.resource.service.rest.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import stackover.resource.service.exception.AccountExistException;
import stackover.resource.service.exception.AccountNotAvailableException;
import stackover.resource.service.exception.AnswerException;
import stackover.resource.service.exception.ApiRequestException;
import stackover.resource.service.exception.CheckedAccountExistsException;
import stackover.resource.service.exception.CheckedQuestionException;
import stackover.resource.service.exception.CommentAnswerException;
import stackover.resource.service.exception.ConstrainException;
import stackover.resource.service.exception.FeignRequestException;
import stackover.resource.service.exception.ProfileServiceException;
import stackover.resource.service.exception.QuestionException;
import stackover.resource.service.exception.TagAlreadyExistsException;
import stackover.resource.service.exception.TagNotFoundException;
import stackover.resource.service.exception.VoteException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CheckedQuestionException.class)
    public ResponseEntity<String> handleCheckedQuestionException(CheckedQuestionException ex) {
        log.error("Handled CheckedQuestionException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CheckedAccountExistsException.class)
    public ResponseEntity<String> handleCheckedAccountExistsException(CheckedAccountExistsException ex) {
        log.error("Handled CheckedAccountExistsException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Handled IllegalArgumentException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(AccountExistException.class)
    public ResponseEntity<String> handleAccountExistException(AccountExistException ex) {
        log.error("Handled AccountExistException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(AccountNotAvailableException.class)
    public ResponseEntity<String> handleAccountNotAvailableException(AccountNotAvailableException ex) {
        log.error("Handled AccountNotAvailableException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }

    @ExceptionHandler(AnswerException.class)
    public ResponseEntity<String> handleAnswerException(AnswerException ex) {
        log.error("Handled AnswerException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<String> handleApiRequestException(ApiRequestException ex) {
        log.error("Handled ApiRequestException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(ex.getMessage());
    }

    @ExceptionHandler(CommentAnswerException.class)
    public ResponseEntity<String> handleCommentAnswerException(CommentAnswerException ex) {
        log.error("Handled CommentAnswerException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ConstrainException.class)
    public ResponseEntity<String> handleConstrainException(ConstrainException ex) {
        log.error("Handled ConstrainException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }


    @ExceptionHandler(FeignRequestException.class)
    public ResponseEntity<String> handleFeignRequestException(FeignRequestException ex) {
        log.error("Handled FeignRequestException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("External service unavailable: " + ex.getMessage());
    }

    @ExceptionHandler(ProfileServiceException.class)
    public ResponseEntity<String> handleProfileServiceException(ProfileServiceException ex) {
        log.error("Handled ProfileServiceException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Profile service error: " + ex.getMessage());
    }

    @ExceptionHandler(QuestionException.class)
    public ResponseEntity<String> handleQuestionException(QuestionException ex) {
        log.error("Handled QuestionException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid question operation: " + ex.getMessage());
    }

    @ExceptionHandler(TagAlreadyExistsException.class)
    public ResponseEntity<String> handleTagAlreadyExistsException(TagAlreadyExistsException ex) {
        log.error("Handled TagAlreadyExistsException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Tag already exists: " + ex.getMessage());
    }

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<String> handleTagNotFoundException(TagNotFoundException ex) {
        log.error("Handled TagNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Tag not found: " + ex.getMessage());
    }

    @ExceptionHandler(VoteException.class)
    public ResponseEntity<String> handleVoteException(VoteException ex) {
        log.error("Handled VoteException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Voting error: " + ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        log.error("Handled RuntimeException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error: " + ex.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleRSE(ResponseStatusException ex) {
        log.warn("ResponseStatusException: {} - {}", ex.getStatusCode(), ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }
}
