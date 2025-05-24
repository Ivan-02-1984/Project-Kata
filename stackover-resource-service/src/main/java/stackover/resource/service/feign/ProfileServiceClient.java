package stackover.resource.service.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import stackover.resource.service.dto.responce.ProfileResponseDto;
import stackover.resource.service.exception.AccountExistException;

@FeignClient(value = "STACKOVER-PROFILE-SERVICE", fallbackFactory = ProfileServiceClient.ProfileServiceFallbackFactory.class)
public interface ProfileServiceClient {

    //TODO не должны получать ResponseEntity - исправить
    @GetMapping("/api/inner/profile")
    ResponseEntity<ProfileResponseDto> getProfileById(@RequestParam Long accountId);
    @Component
    class ProfileServiceFallbackFactory implements FallbackFactory<FallbackWithFactory> {
        @Override
        public FallbackWithFactory create(Throwable cause) {
            return new FallbackWithFactory(cause.getMessage());
        }
    }

    record FallbackWithFactory(String reason) implements ProfileServiceClient {
        @Override
        public ResponseEntity<ProfileResponseDto> getProfileById(Long accountId) {
            String responseMessage = """
                    Profile with id %s not found
                    """.formatted(accountId);
            throw new AccountExistException(responseMessage);
        }
    }
}
