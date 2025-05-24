package stackover.auth.service.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import stackover.auth.service.dto.profile.ProfilePostDto;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@FeignClient(name = "STACKOVER-PROFILE-SERVICE", fallbackFactory = ProfileServiceClient.ProfilesClientFallbackFactory.class)
public interface ProfileServiceClient {

    @PostMapping("/api/inner/profile")
    void createProfileByPostDto(@RequestBody ProfilePostDto profilePostDto);

    @Slf4j
    @Component
    class ProfilesClientFallbackFactory implements FallbackFactory<ProfileServiceClient> {
        @Override
        public ProfileServiceClient create(Throwable cause) {
            log.error("🚨 Ошибка соединения с STACKOVER-PROFILE-SERVICE", cause);

            return new ProfileServiceClient() {
                @Override
                public void createProfileByPostDto(ProfilePostDto profilePostDto) {
                    log.error("""
                        🔥 Ошибка создания профиля
                        Account ID: {}
                        Email: {}
                        Причина: {}
                        Тип ошибки: {}""",
                            profilePostDto.accountId(),
                            profilePostDto.email(),
                            cause.getMessage(),
                            cause.getClass().getSimpleName());

                    if (cause instanceof feign.FeignException feignEx) {
                        log.error("HTTP Status: {}, Response: {}",
                                feignEx.status(),
                                feignEx.contentUTF8());
                    }
                }
            };
        }
    }
}