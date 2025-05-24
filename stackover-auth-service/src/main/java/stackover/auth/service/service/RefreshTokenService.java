package stackover.auth.service.service;

import stackover.auth.service.model.Account;

public interface RefreshTokenService {
    String generateAndSaveRefreshToken(Account account);
    Account validateRefreshToken(String token);
    void revokeRefreshToken(String token);
    String refreshAccessToken(Account account);
}
