package stackover.resource.service.rest.out;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stackover.resource.service.dto.response.AnswerResponseDto;
import stackover.resource.service.exception.CheckedAccountExistsException;
import stackover.resource.service.exception.CheckedQuestionException;
import stackover.resource.service.service.dto.AnswerResponseDtoService;
import stackover.resource.service.service.entity.VotingFacade;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user/question/{questionId}/answer")
@RequiredArgsConstructor
public class ResourceAnswerController {

    private final VotingFacade votingFacade;
    private final AnswerResponseDtoService answerResponseDtoServiceImpl;

    @Operation(
            summary = "Голосование против ответа",
            description = "Позволяет текущему пользователю проголосовать против указанного ответа. " +
                    "Повторное голосование не допускается. Нельзя голосовать за свой собственный ответ. " +
                    "Автор ответа теряет 5 очков репутации.",
            parameters = {
                    @Parameter(name = "questionId", description = "ID вопроса", example = "1", required = true),
                    @Parameter(name = "answerId", description = "ID ответа", example = "10", required = true),
                    @Parameter(name = "accountId", description = "ID пользователя, который голосует",
                            example = "2", required = true, in = ParameterIn.QUERY)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Голос успешно учтен"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса или попытка голосовать за свой ответ"),
            @ApiResponse(responseCode = "404", description = "Ответ не найден"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @PostMapping("/{answerId}/downVote")
    public ResponseEntity<Long> downVoteAnswer(
            @PathVariable Long questionId,
            @PathVariable Long answerId,
            @RequestParam Long accountId
    ) {
        log.info("Начало обработки голоса против ответа. QuestionID: {}, AnswerID: {}, AccountID: {}",
                questionId, answerId, accountId);
        return ResponseEntity.ok(votingFacade.downVote(answerId, accountId));
    }

    @GetMapping
    @Operation(
            summary = "Получить все ответы на вопрос",
            description = "Возвращает список ответов для указанного вопроса по его ID. Требуется ID аккаунта для проверки доступа."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список ответов успешно получен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnswerResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректный ID вопроса или аккаунта",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Аккаунт или вопрос не найдены",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Ошибка проверки аккаунта",
                    content = @Content
            )
    })
    public ResponseEntity<List<AnswerResponseDto>> getAllAnswers(
            @Parameter(description = "ID вопроса", example = "1")
            @PathVariable @Positive Long questionId,
            @Parameter(description = "ID аккаунта", example = "1")
            @RequestParam @Positive Long accountId
    ) throws CheckedQuestionException, CheckedAccountExistsException {
        log.info("Request to get answers for question ID {} by account ID {}", questionId, accountId);


        List<AnswerResponseDto> answerDtos = answerResponseDtoServiceImpl.getAnswersDtoByQuestionId(questionId, accountId);

        log.info("Successfully retrieved {} answers for question ID {}", answerDtos.size(), questionId);
        return ResponseEntity.ok(answerDtos);
    }


    @Operation(
            summary = "Голосование за ответ",
            description = "Позволяет текущему пользователю проголосовать за указанный ответ. " +
                    "Повторное голосование не допускается. Нельзя голосовать за свой собственный ответ. " +
                    "Автор ответа получает 10 очков репутации."
    )
    @PostMapping("/{answerId}/upVote")
    public ResponseEntity<Long> upVoteAnswer(
            @Parameter(description = "ID вопроса", example = "1")
            @PathVariable Long questionId,
            @Parameter(description = "ID ответа", example = "10")
            @PathVariable Long answerId,
            @Parameter(description = "ID пользователя, который голосует", example = "2")
            @RequestParam Long accountId
    ) {
        log.info("User {} votes UP for answer {} in question {}", accountId, answerId, questionId);
        long count = votingFacade.upVote(answerId, accountId);
        return ResponseEntity.ok(count);
    }
}
