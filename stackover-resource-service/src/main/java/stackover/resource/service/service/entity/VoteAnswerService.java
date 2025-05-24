package stackover.resource.service.service.entity;

import stackover.resource.service.entity.question.answer.Answer;
import stackover.resource.service.entity.user.User;

public interface VoteAnswerService {

    long downVoteInternal(Answer answer, User voter);

    long upVoteInternal(Answer answer, User voter);
}

