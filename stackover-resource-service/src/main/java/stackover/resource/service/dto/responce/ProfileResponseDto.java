package stackover.resource.service.dto.responce;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ProfileResponseDto(
        @Schema(description = "ID профиля")
        long profileId,
        @Schema(description = "ID аккаунта")
        long accountId,
        @Schema(description = "Email пользователя")
        String email,
        @Schema(description = "Полное имя пользователя")
        String fullName,
        @Schema(description = "Город пользователя")
        String city,
        @Schema(description = "Дата создания профиля")
        LocalDateTime persistDateTime,
        @Schema(description = "Ссылка на сайт пользователя")
        String linkSite,
        @Schema(description = "Ссылка на GitHub пользователя")
        String linkGitHub,
        @Schema(description = "Ссылка на VK пользователя")
        String linkVk,
        @Schema(description = "Описание пользователя")
        String about,
        @Schema(description = "Ссылка на изображение профиля")
        String imageLink,
        @Schema(description = "Никнейм пользователя")
        String nickname,
        @Schema(description = "Флаг активности профиля")
        boolean enabled) {

}
