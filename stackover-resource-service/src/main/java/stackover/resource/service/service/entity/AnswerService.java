package stackover.resource.service.service.entity;

import stackover.resource.service.entity.question.answer.Answer;
import stackover.resource.service.entity.user.User;

public interface AnswerService {
    Answer getAnswerForVoting(Long answerId, User voter);
}