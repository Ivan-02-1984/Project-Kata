package stackover.resource.service.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stackover.resource.service.entity.question.answer.Answer;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.entity.user.reputation.Reputation;
import stackover.resource.service.entity.user.reputation.ReputationType;
import stackover.resource.service.repository.entity.ReputationRepository;
import stackover.resource.service.service.entity.ReputationService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReputationServiceImpl implements ReputationService {

    private final ReputationRepository reputationRepository;

    @Override
    @Transactional
    public void downVoteReputation(Long senderId, Long authorId, Answer answer) {
        Optional<Reputation> reputationOpt = reputationRepository
                .findBySenderIdAndAnswerId(senderId, answer.getId());

        if (reputationOpt.isPresent()) {
            Reputation reputation = reputationOpt.get();

            if (reputation.getCount() == -5) {
                return;
            }

            reputation.setCount(-5);
            reputation.setType(ReputationType.VOTE_ANSWER);
            reputationRepository.save(reputation);
        } else {
            Reputation reputation = new Reputation();


            User author = answer.getUser();
            User sender = new User();
            sender.setId(senderId);

            reputation.setAuthor(author);
            reputation.setSender(sender);
            reputation.setAnswer(answer);
            reputation.setCount(-5);
            reputation.setType(ReputationType.VOTE_ANSWER);
            reputationRepository.save(reputation);
        }
    }

    @Override
    @Transactional
    public void upVoteReputation(Long senderId, Long authorId, Answer answer) {
        Optional<Reputation> reputationOpt = reputationRepository
                .findBySenderIdAndAnswerId(senderId, answer.getId());

        if (reputationOpt.isPresent()) {
            Reputation reputation = reputationOpt.get();

            if (reputation.getCount() == 10) {
                return;
            }

            reputation.setCount(10);
            reputation.setType(ReputationType.VOTE_ANSWER);
            reputationRepository.save(reputation);
        } else {
            Reputation reputation = new Reputation();

            User author = answer.getUser();
            User sender = new User();
            sender.setId(senderId);

            reputation.setAuthor(author);
            reputation.setSender(sender);
            reputation.setAnswer(answer);
            reputation.setCount(10);
            reputation.setType(ReputationType.VOTE_ANSWER);
            reputationRepository.save(reputation);
        }
    }
}