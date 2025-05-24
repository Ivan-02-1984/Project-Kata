package stackover.auth.service.dto.request;

import javax.validation.constraints.Email;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Запрос на вход")
public record LoginRequestDto(
        @Schema(description = "Email", example = "user@example.com") @Email String email,
        @Schema(description = "Пароль") String password
) {}
