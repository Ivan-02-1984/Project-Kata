package stackover.resource.service.rest.out;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stackover.resource.service.dto.responce.QuestionResponseDto;
import stackover.resource.service.exception.QuestionException;
import stackover.resource.service.service.dto.QuestionDtoService;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/question")
@Tag(name = "Question API", description = "API для работы с вопросами")
public class ResourceQuestionController {
    private final QuestionDtoService questionDtoService;

    @Operation(
            summary = "Получение вопроса по ID",
            description = "Возвращает вопрос с указанным ID, если он существует и доступен для указанного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Вопрос успешно найден и возвращен",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = QuestionResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверные параметры запроса (например, отрицательный ID аккаунта)"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Вопрос не найден или недоступен для указанного пользователя"
                    )
            }
    )

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponseDto> getQuestionById(
            @PathVariable @Positive Long questionId,
            @RequestParam @Positive Long accountId) {

        try {
            QuestionResponseDto questionDto = questionDtoService.getQuestionWithUserCheck(questionId, accountId);
            log.info("Успешно возвращен вопрос: {}", questionDto);
            return ResponseEntity.ok(questionDto);
        } catch (QuestionException e) {
            log.warn("Вопрос с ID {} не найден", questionId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
