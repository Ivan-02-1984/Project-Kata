package stackover.auth.service.dto.token;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ на обновление токена")
public record RefreshTokenResponseDto(
        @Schema(description = "Новый токен доступа") String accessToken,
        @Schema(description = "Новый refresh токен") String refreshToken,
        @Schema(description = "Заблокирован ли токен") boolean blocked
) {}
