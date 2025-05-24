package stackover.auth.service.dto.account;

import javax.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;
import stackover.auth.service.util.enums.RoleNumEnum;

@Schema(description = "Данные об аккаунте")
public record AccountResponseDto(
        @Schema(description = "ID аккаунта") Long id,
        @Schema(description = "Email аккаунта", example = "user@example.com") @NotEmpty String email,
        @Schema(description = "Роль аккаунта") @NotEmpty RoleNumEnum role,
        @Schema(description = "Аккаунт активирован") boolean enabled
) {}
