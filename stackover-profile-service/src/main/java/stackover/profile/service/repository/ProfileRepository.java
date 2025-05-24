package stackover.profile.service.repository;

import stackover.profile.service.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByAccountId(Long accountId);

    boolean existsByAccountId(Long accountId);
}