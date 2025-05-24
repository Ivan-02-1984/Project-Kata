package stackover.auth.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stackover.auth.service.model.Account;
import stackover.auth.service.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByAccount_Id(Long userId);
    void deleteByToken(String token);
    Optional<RefreshToken> findByAccountAndToken(Account account, String token);
}