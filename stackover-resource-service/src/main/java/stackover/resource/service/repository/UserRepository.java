package stackover.resource.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stackover.resource.service.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
