package stackover.resource.service.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import stackover.resource.service.exception.AccountExistException;

@FeignClient(value = "stackover-auth-service", fallbackFactory = AuthServiceClient.AuthServiceFallbackFactory.class)
public interface AuthServiceClient {

    @GetMapping("/api/internal/account/{accountId}/exists")
    Boolean isAccountExist(@PathVariable Long accountId);




    @Component
    class AuthServiceFallbackFactory implements FallbackFactory<FallbackWithFactory> {
        @Override
        public FallbackWithFactory create(Throwable cause) {
            return new FallbackWithFactory(cause.getMessage());
        }
    }

    record FallbackWithFactory(String reason) implements AuthServiceClient {
        @Override
        public Boolean isAccountExist(@PathVariable Long accountId) {
            String responseMessage = """
                    Account with id %s not found
                    """.formatted(accountId);
            throw new AccountExistException(responseMessage);
        }
    }
}
