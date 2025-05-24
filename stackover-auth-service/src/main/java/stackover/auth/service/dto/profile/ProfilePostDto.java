package stackover.auth.service.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Данные профиля для создания")
public record ProfilePostDto(
        @Schema(description = "ID аккаунта") Long accountId,
        @Schema(description = "Email аккаунта") String email,
        @Schema(description = "Полное имя") String fullName,
        @Schema(description = "Город проживания") String city,
        @Schema(description = "Дата регистрации") LocalDateTime persistDateTime,
        @Schema(description = "Ссылка на сайт") String linkSite,
        @Schema(description = "Ссылка на GitHub") String linkGitHub,
        @Schema(description = "Ссылка на VK") String linkVk,
        @Schema(description = "Информация о пользователе") String about,
        @Schema(description = "Ссылка на изображение профиля") String imageLink,
        @Schema(description = "Никнейм пользователя") String nickname
) {}
