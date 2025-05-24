package stackover.resource.service.service.entity.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import stackover.resource.service.entity.question.answer.Answer;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.repository.entity.AnswerRepository;
import stackover.resource.service.service.entity.AnswerService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    @Override
    @Transactional(readOnly = true)
    public Answer getAnswerForVoting(Long answerId, User voter) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ответ не найден"));

        if (answer.getUser().getId().equals(voter.getId())) {
            log.warn("Попытка голосовать за свой ответ: userId={}", voter.getId());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Нельзя голосовать за свой ответ");
        }

        return answer;
    }
}
