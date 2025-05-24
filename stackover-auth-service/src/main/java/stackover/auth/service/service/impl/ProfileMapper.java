package stackover.auth.service.service.impl;

import org.springframework.stereotype.Component;
import stackover.auth.service.dto.profile.ProfilePostDto;
import stackover.auth.service.dto.request.SignupRequestDto;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Component
public class ProfileMapper {

    // Маппинг SignupRequestDto -> ProfilePostDto
    public ProfilePostDto toProfilePostDto(SignupRequestDto signupRequestDto, Long accountId) {
        Objects.requireNonNull(signupRequestDto, "SignupRequestDto cannot be null");
        Objects.requireNonNull(accountId, "Account ID cannot be null");

        // Дата регистрации остается без изменений, так как она уже является LocalDateTime
        LocalDateTime persistDateTime = getPersistDateTime(signupRequestDto);

        return new ProfilePostDto(
                accountId,
                requireNonNullField(signupRequestDto.email(), "Email cannot be null"),
                requireNonNullField(signupRequestDto.fullName(), "Full name cannot be null"),
                signupRequestDto.city(),
                persistDateTime,
                signupRequestDto.linkSite(),
                signupRequestDto.linkGitHub(),
                signupRequestDto.linkVk(),
                signupRequestDto.about(),
                signupRequestDto.imageLink(),
                requireNonNullField(signupRequestDto.nickname(), "Nickname cannot be null")
        );
    }

    // Обрабатываем persistDateTime (возможно, нужно просто проверить на null)
    private LocalDateTime getPersistDateTime(SignupRequestDto signupRequestDto) {
        return Optional.ofNullable(signupRequestDto.persistDateTime())
                .orElseGet(() -> LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0));  // В случае отсутствия даты, устанавливаем текущую с нулевым временем
    }

    // Вспомогательный метод для проверки обязательных полей на null
    private String requireNonNullField(String field, String errorMessage) {
        return Objects.requireNonNull(field, errorMessage);
    }
}
