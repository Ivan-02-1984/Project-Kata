package stackover.auth.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Запрос на регистрацию")
public record SignupRequestDto(
        @Schema(description = "Пароль") String password,
        @Schema(description = "Имя роли") String roleName,
        @Schema(description = "Email", example = "user@example.com") String email,
        @Schema(description = "Полное имя") String fullName,
        @Schema(description = "Город") String city,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @Schema(description = "Дата регистрации") LocalDateTime persistDateTime,
        @Schema(description = "Ссылка на сайт") String linkSite,
        @Schema(description = "Ссылка на GitHub") String linkGitHub,
        @Schema(description = "Ссылка на VK") String linkVk,
        @Schema(description = "О себе") String about,
        @Schema(description = "Ссылка на изображение") String imageLink,
        @Schema(description = "Никнейм") String nickname
) {}
