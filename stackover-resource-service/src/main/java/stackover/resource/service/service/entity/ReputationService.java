package stackover.resource.service.service.entity;

import stackover.resource.service.entity.question.answer.Answer;

public interface ReputationService {
    void downVoteReputation(Long senderId, Long authorId, Answer answer);

    void upVoteReputation(Long senderId, Long authorId, Answer answer);
}
