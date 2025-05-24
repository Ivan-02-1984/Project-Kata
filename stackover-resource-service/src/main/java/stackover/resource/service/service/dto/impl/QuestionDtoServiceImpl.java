package stackover.resource.service.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import stackover.resource.service.dto.responce.ProfileResponseDto;
import stackover.resource.service.dto.responce.QuestionResponseDto;
import stackover.resource.service.exception.QuestionException;
import stackover.resource.service.feign.AuthServiceClient;
import stackover.resource.service.feign.ProfileServiceClient;
import stackover.resource.service.repository.dto.QuestionDtoRepository;
import stackover.resource.service.service.dto.QuestionDtoService;
import stackover.resource.service.service.dto.TagDtoService;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionDtoServiceImpl implements QuestionDtoService {
    private final QuestionDtoRepository questionDtoRepository;
    private final TagDtoService tagDtoService;
    private final ProfileServiceClient profileServiceClient;
    private final AuthServiceClient authServiceClient; // TODO: Заглушка - удалить после реализации сервиса аутентификации

    @Override
    public QuestionResponseDto getQuestionWithUserCheck(Long questionId, Long accountId) {

        if (questionId == null || accountId == null) {
            throw new IllegalArgumentException("ID вопроса и аккаунта не могут быть null");
        }

        QuestionResponseDto dto = questionDtoRepository
                .getQuestionDtoByQuestionIdAndUserId(questionId, resolveAccountId(accountId))
                .orElseThrow(() -> new QuestionException("Вопрос не найден"));


        return new QuestionResponseDto(
                dto.id(),
                dto.title(),
                dto.authorId(),
                dto.authorName(),
                getEmailFromProfile(dto.authorId()),
                dto.authorImage(),
                dto.description(),
                dto.viewCount(),
                dto.authorReputation(),
                dto.countAnswer(),
                dto.countValuable(),
                dto.persistDateTime(),
                dto.lastUpdateDateTime(),
                dto.countVote(),
                dto.voteTypeQuestion(),
                tagDtoService.getTagsByQuestionId(questionId)
        );
    }

    private Long resolveAccountId(Long accountId) {
        try {
            Boolean accountExists = authServiceClient.isAccountExist(accountId);
            if (accountExists == null || !accountExists) {
                log.warn("Сервис аутентификации не доступен или пользователь не найден.");
                return accountId;
            }
            return accountId;
        } catch (Exception e) {
            log.error("Ошибка при проверке существования аккаунта: {}", e.getMessage());
            return accountId;
        }
    }

    private String getEmailFromProfile(Long accountId) {
        ResponseEntity<ProfileResponseDto> response = profileServiceClient.getProfileById(accountId);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().email();
        }
        log.warn("Не удалось получить email для пользователя {}", accountId);
        return null;
    }
}
