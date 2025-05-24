package stackover.resource.service.repository.entity;



import org.springframework.data.jpa.repository.JpaRepository;
import stackover.resource.service.entity.question.answer.Answer;
import stackover.resource.service.entity.question.answer.VoteAnswer;
import stackover.resource.service.entity.question.answer.VoteTypeAnswer;
import stackover.resource.service.entity.user.User;

import java.util.Optional;

public interface VoteAnswerRepository extends JpaRepository<VoteAnswer, Long> {

    Optional<VoteAnswer> findByAnswerAndUser(Answer answer, User user);

    Optional<VoteAnswer> findFirstByAnswerId(Long answerId);

    long countByAnswerIdAndVoteTypeAnswer(Long answerId, VoteTypeAnswer voteTypeAnswer);
}
