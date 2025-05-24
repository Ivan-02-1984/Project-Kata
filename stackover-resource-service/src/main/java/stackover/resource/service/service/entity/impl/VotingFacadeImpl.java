package stackover.resource.service.service.entity.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import stackover.resource.service.dto.responce.ProfileResponseDto;
import stackover.resource.service.entity.question.answer.Answer;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.exception.AccountNotAvailableException;
import stackover.resource.service.exception.ConstrainException;
import stackover.resource.service.feign.AuthServiceClient;
import stackover.resource.service.feign.ProfileServiceClient;
import stackover.resource.service.service.entity.AnswerService;
import stackover.resource.service.service.entity.VoteAnswerService;
import stackover.resource.service.service.entity.VotingFacade;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VotingFacadeImpl implements VotingFacade {

    private final VoteAnswerService voteAnswerService;
    private final AnswerService answerService;
    private final AuthServiceClient authServiceClient;
    private final ProfileServiceClient profileServiceClient;

    private User validateUser(Long accountId) {
        if (accountId == null || accountId <= 0) {
            throw new ConstrainException("ID аккаунта должно быть положительным");
        }

        if (!authServiceClient.isAccountExist(accountId)) {
            throw new AccountNotAvailableException("Аккаунт не существует или недоступен");
        }

        ProfileResponseDto profile = Optional.ofNullable(profileServiceClient.getProfileById(accountId).getBody())
                .orElseThrow(() -> new AccountNotAvailableException("Профиль не найден"));

        User user = new User();
        user.setId(profile.profileId());
        user.setFullName(profile.fullName());
        user.setEmail(profile.email());
        user.setNickname(profile.nickname());

        return user;
    }

    @Override
    @Transactional
    public long downVote(Long answerId, Long accountId) {
        try {
            User voter = validateUser(accountId);
            Answer answer = answerService.getAnswerForVoting(answerId, voter);
            return voteAnswerService.downVoteInternal(answer, voter);
        } catch (AccountNotAvailableException | ConstrainException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    @Transactional
    public long upVote(Long answerId, Long accountId) {
        try {
            User voter = validateUser(accountId);
            Answer answer = answerService.getAnswerForVoting(answerId, voter);
            return voteAnswerService.upVoteInternal(answer, voter);
        } catch (AccountNotAvailableException | ConstrainException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}