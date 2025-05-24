package stackover.resource.service.dto.responce;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UserResponseDto(
        @Schema(description = "id пользователя")
        Long id,
        @Schema(description = "почта пользователя")
        String email,
        @Schema(description = "имя пользователя")
        String fullName,
        @Schema(description = "ссылка на изображение пользователя")
        String linkImage,
        @Schema(description = "город пользователя")
        String city,
        @Schema(description = "репутация пользователя")
        Long reputation,
        @Schema(description = "дата регистрации пользователя")
        LocalDateTime registrationDate,
        @Schema(description = "количество голосов пользователя" )
        Long votes

        /*
         * TODO: Добавить после реализации топа тегов.
         * Поле должно содержать топ-3 тега пользователя.
         * Пример:
         * List<TagResponseDto> listTop3TagDto
         */
) {
        public UserResponseDto(Long id, String fullName, String linkImage, String city,
                               Long reputation, LocalDateTime registrationDate, Long votes) {
                this(id, null, fullName, linkImage, city, reputation, registrationDate, votes);
        }

        public UserResponseDto() {
                this(null, null, null, null,
                        null, null, null, null);
        }

}
