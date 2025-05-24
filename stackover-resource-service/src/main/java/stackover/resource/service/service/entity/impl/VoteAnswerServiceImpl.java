package stackover.resource.service.service.entity.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stackover.resource.service.entity.question.answer.Answer;
import stackover.resource.service.entity.question.answer.VoteAnswer;
import stackover.resource.service.entity.question.answer.VoteTypeAnswer;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.repository.entity.VoteAnswerRepository;
import stackover.resource.service.service.entity.ReputationService;
import stackover.resource.service.service.entity.VoteAnswerService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteAnswerServiceImpl implements VoteAnswerService {

    private final VoteAnswerRepository voteAnswerRepository;
    private final ReputationService reputationService;

    @Override
    @Transactional
    public long downVoteInternal(Answer answer, User voter) {
        return voteInternal(answer, voter, VoteTypeAnswer.DOWN);
    }

    @Override
    @Transactional
    public long upVoteInternal(Answer answer, User voter) {
        return voteInternal(answer, voter, VoteTypeAnswer.UP);
    }

    private long voteInternal(Answer answer, User voter, VoteTypeAnswer newVoteType) {
        Optional<VoteAnswer> existingVoteOpt = voteAnswerRepository.findByAnswerAndUser(answer, voter);

        if (existingVoteOpt.isPresent()) {
            VoteAnswer existingVote = existingVoteOpt.get();

            if (existingVote.getVoteTypeAnswer() == newVoteType) {
                log.info("Пользователь уже голосовал '{}'", newVoteType);
                return voteAnswerRepository.countByAnswerIdAndVoteTypeAnswer(answer.getId(), newVoteType);
            }

            existingVote.setVoteTypeAnswer(newVoteType);
            voteAnswerRepository.save(existingVote);
            log.info("Голос изменён на '{}'", newVoteType);
        } else {
            VoteAnswer newVote = new VoteAnswer(voter, answer, newVoteType);
            voteAnswerRepository.save(newVote);
            log.info("Добавлен новый голос '{}'", newVoteType);
        }

        if (newVoteType == VoteTypeAnswer.UP) {
            reputationService.upVoteReputation(voter.getId(), answer.getUser().getId(), answer);
        } else {
            reputationService.downVoteReputation(voter.getId(), answer.getUser().getId(), answer);
        }

        return voteAnswerRepository.countByAnswerIdAndVoteTypeAnswer(answer.getId(), newVoteType);
    }
}