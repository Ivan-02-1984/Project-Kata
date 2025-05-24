package stackover.resource.service.service.dto.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stackover.resource.service.dto.response.AnswerResponseDto;
import stackover.resource.service.exception.AccountExistException;
import stackover.resource.service.exception.CheckedAccountExistsException;
import stackover.resource.service.feign.AuthServiceClient;
import stackover.resource.service.feign.ProfileServiceClient;
import stackover.resource.service.repository.AnswerDtoRepository;
import stackover.resource.service.service.dto.AnswerResponseDtoService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerResponseDtoServiceImpl implements AnswerResponseDtoService {

    private final AnswerDtoRepository answerDtoRepository;
    private final ProfileServiceClient profileServiceClient;
    private final AuthServiceClient authServiceClient;

    public List<AnswerResponseDto> getAnswersDtoByQuestionId(Long questionId, Long accountId) throws CheckedAccountExistsException {
        log.info("Fetching AnswerResponseDto for question ID {} and account ID {}", questionId, accountId);
        //TODO Заглушка, заменить на вызов AuthServiceClient после реализации безопасности
        if(!authServiceClient.isAccountExist(accountId)){
            throw new AccountExistException("Аккаунт с Id "+accountId+" не существует");
        };

        List<AnswerResponseDto> dtosWithoutEmail = answerDtoRepository.getAnswersDtoByQuestionId(questionId, accountId);
        return dtosWithoutEmail.stream().map(dto->(new AnswerResponseDto(
                dto.id(),
                dto.userId(),
                profileServiceClient.getProfileById(accountId).getBody().email(),
                dto.questionId(),
                dto.body(),
                dto.persistDate(),
                dto.isHelpful(),
                dto.dateAccept(),
                dto.countValuable(),
                dto.countUserReputation(),
                dto.image(),
                dto.nickname(),
                dto.countVote(),
                dto.voteTypeAnswer()
        ))).collect(Collectors.toList());
    }
}
