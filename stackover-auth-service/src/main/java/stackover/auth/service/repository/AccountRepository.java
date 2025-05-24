package stackover.auth.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stackover.auth.service.model.Account;
import stackover.auth.service.util.enums.RoleNumEnum;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

        Optional<Account> findByEmail(String email);

        boolean existsByIdAndRoleName(Long id, RoleNumEnum roleName);
}

