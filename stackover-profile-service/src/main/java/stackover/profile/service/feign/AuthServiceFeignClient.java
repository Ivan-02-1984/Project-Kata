package stackover.profile.service.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import stackover.profile.service.dto.AccountResponseDto;
import stackover.profile.service.exception.AccountExistException;

@FeignClient(name = "STACKOVER-AUTH-SERVICE", fallbackFactory = AuthServiceFeignClient.AuthServiceFallbackFactory.class,
        path = "/api/internal/account")
public interface AuthServiceFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<AccountResponseDto> getAccountById(@PathVariable long id);


    @Component
    class AuthServiceFallbackFactory implements FallbackFactory<FallbackWithFactory> {
        @Override
        public FallbackWithFactory create(Throwable cause) {
            return new FallbackWithFactory(cause.getMessage());
        }
    }

    record FallbackWithFactory(String reason) implements AuthServiceFeignClient {
        @Override
        public ResponseEntity<AccountResponseDto> getAccountById(long id) {
            String responseMessage = """
                    Account with id %s not found
                    """.formatted(id);
            throw new AccountExistException(responseMessage);
        }
    }
}
