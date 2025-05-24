package stackover.resource.service.repository.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stackover.resource.service.entity.user.reputation.Reputation;

import java.util.Optional;

@Repository
public interface ReputationRepository extends JpaRepository<Reputation, Long> {

    Optional<Reputation> findBySenderIdAndAnswerId(Long senderId, Long id);
}

