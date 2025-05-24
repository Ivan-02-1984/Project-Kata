package stackover.resource.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import stackover.resource.service.entity.question.answer.VoteTypeAnswer;

import java.time.LocalDateTime;

@Schema(description = "DTO для представления ответа на вопрос")
public record AnswerResponseDto(
        @Schema(description = "id ответа на вопрос", example = "1") Long id,
        @Schema(description = "id пользователя", example = "1") Long userId,
        @Schema(description = "почта пользователя", example = "user@example.com") @Nullable String email,
        @Schema(description = "id вопроса", example = "1") Long questionId,
        @Schema(description = "текст ответа", example = "Это пример ответа на вопрос", required = true)
        @NotEmpty(message = "Текст ответа не может быть пустым")
        @NotBlank(message = "Текст ответа не может состоять только из пробелов")
        @NotNull(message = "Текст ответа обязателен")
        String body,
        @Schema(description = "дата создания ответа", example = "2025-04-09T12:00:00") LocalDateTime persistDate,
        @Schema(description = "польза ответа", example = "true") Boolean isHelpful,
        @Schema(description = "дата решения вопроса", example = "2025-04-10T12:00:00") LocalDateTime dateAccept,
        @Schema(description = "рейтинг ответа", example = "10") Long countValuable,
        @Schema(description = "рейтинг юзера", example = "100") Long countUserReputation,
        @Schema(description = "ссылка на картинку пользователя", example = "http://example.com/image.jpg") String image,
        @Schema(description = "никнейм пользователя", example = "user123") String nickname,
        @Schema(description = "Количество голосов", example = "5") Long countVote,
        @Schema(description = "тип голоса", example = "UP") VoteTypeAnswer voteTypeAnswer
) {
    public AnswerResponseDto(
            Long id,
            Long userId,
            Long questionId,
            String body,
            LocalDateTime persistDate,
            Boolean isHelpful,
            LocalDateTime dateAccept,
            Long countValuable,
            Long countUserReputation,
            String image,
            String nickname,
            Long countVote,
            VoteTypeAnswer voteTypeAnswer
    ) {
        this(
                id,
                userId,
                null, // email явно не передается
                questionId,
                body,
                persistDate,
                isHelpful,
                dateAccept,
                countValuable,
                countUserReputation,
                image,
                nickname,
                countVote,
                voteTypeAnswer
        );
    }

}