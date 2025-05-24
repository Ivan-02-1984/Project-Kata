package stackover.auth.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stackover.auth.service.model.Role;
import stackover.auth.service.util.enums.RoleNumEnum;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleNumEnum name);
}
