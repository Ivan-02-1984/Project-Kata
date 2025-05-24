package stackover.auth.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ при аутентификации")
public record AuthResponseDto(
        @Schema(description = "ID аккаунта") Long accountId,
        @Schema(description = "Email аккаунта") String email,
        @Schema(description = "JWT токен доступа") String accessToken,
        @Schema(description = "Время истечения токена") Long expiresIn,
        @Schema(description = "JWT токен обновления") String refreshToken
) {}
