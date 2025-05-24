package stackover.auth.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import stackover.auth.service.exception.InvalidJwtException;
import stackover.auth.service.model.Account;
import stackover.auth.service.util.enums.RoleNumEnum;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long jwtExpirationMs;

    @Value("${jwt.refresh-expiration-ms}")
    private long refreshExpirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        log.info("Инициализирован JWT ключ: {}", jwtSecret); // Добавьте это
    }

    // Универсальный метод построения токена
    private String buildToken(Account account, long expiration, Map<String, Object> additionalClaims) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(account.getId().toString())
                .claim("email", account.getEmail())
                .claim("role", account.getRole().getName().name())
                .addClaims(additionalClaims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateAccessToken(Account account) {
        String token = buildToken(account, jwtExpirationMs, Collections.emptyMap());
        log.debug("Сгенерирован access токен для {}", account.getEmail());
        return token;
    }

    public String generateRefreshToken(Account account) {
        String token = buildToken(account, refreshExpirationMs,
                Map.of("type", "refresh"));
        log.debug("Сгенерирован refresh токен для {}", account.getEmail());
        return token;
    }

    public boolean validateToken(String token) {
        try {
            // Разделяем токен на части для анализа
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                log.error("Неверный формат токена");
                return false;
            }

            log.debug("Header: {}", new String(Base64.getUrlDecoder().decode(parts[0])));
            log.debug("Payload: {}", new String(Base64.getUrlDecoder().decode(parts[1])));

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            log.error("Полная ошибка валидации:", ex);
            return false;
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmailFromToken(String token) {
        try {
            Claims claims = parseClaims(token);
            return claims.get("email", String.class);
        } catch (Exception e) {
            log.error("Ошибка извлечения email из токена", e);
            throw new InvalidJwtException("Неверный формат токена");
        }
    }

    public long getExpirationTime() {
        return jwtExpirationMs / 1000; // Возвращаем в секундах
    }

    public TokenData extractTokenData(String token) {
        Claims claims = parseClaims(token);
        return new TokenData(
                Long.parseLong(claims.getSubject()),
                claims.get("email", String.class),
                RoleNumEnum.valueOf(claims.get("role", String.class)),
                claims.get("type", String.class), // Добавляем извлечение типа токена
                claims.getExpiration()
        );
    }

    @Getter
    @AllArgsConstructor
    public static class TokenData {
        private final Long accountId;
        private final String email;
        private final RoleNumEnum role;
        private final String type; // Добавляем поле для типа токена
        private final Date expiration;
    }
}