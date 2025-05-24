package stackover.resource.service.service.entity;

public interface VotingFacade {
    long downVote(Long answerId, Long accountId);
    long upVote(Long answerId, Long accountId);
}

