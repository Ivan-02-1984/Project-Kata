package stackover.auth.service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import stackover.auth.service.exception.InvalidJwtException;
import stackover.auth.service.model.Account;
import stackover.auth.service.security.JwtTokenProvider;
import stackover.auth.service.service.AccountService;
import stackover.auth.service.service.RefreshTokenService;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountService accountService;

    @Override
    public String generateAndSaveRefreshToken(Account account) {
        String token = jwtTokenProvider.generateRefreshToken(account);
        log.info("Создан refresh токен для пользователя {}", account.getEmail());
        return token;
    }

    @Override
    public Account validateRefreshToken(String token) {
        try {
            String cleanedToken = token.replaceAll("^\"|\"$", "");

            if (!jwtTokenProvider.validateToken(cleanedToken)) {
                log.warn("Невалидная подпись токена");
                throw new InvalidJwtException("Недействительный токен");
            }

            JwtTokenProvider.TokenData tokenData = jwtTokenProvider.extractTokenData(cleanedToken);
            return accountService.findByEmail(tokenData.getEmail())
                    .orElseThrow(() -> {
                        log.warn("Аккаунт не найден для refresh токена");
                        return new InvalidJwtException("Аккаунт не существует");
                    });
        } catch (Exception ex) {
            log.error("Ошибка валидации токена", ex);
            throw new InvalidJwtException("Недействительный токен");
        }
    }


    @Override
    public void revokeRefreshToken(String token) {
        log.info("Refresh токен отозван (JWT механизм инвалидации)");
    }

    @Override
    public String refreshAccessToken(Account account) {
        String newToken = jwtTokenProvider.generateAccessToken(account);
        log.debug("Обновлен access токен для {}", account.getEmail());
        return newToken;
    }
}