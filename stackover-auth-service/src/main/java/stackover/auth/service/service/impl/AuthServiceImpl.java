package stackover.auth.service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import stackover.auth.service.dto.request.LoginRequestDto;
import stackover.auth.service.dto.request.SignupRequestDto;
import stackover.auth.service.dto.response.AuthResponseDto;
import stackover.auth.service.dto.token.RefreshTokenResponseDto;
import stackover.auth.service.exception.InvalidJwtException;
import stackover.auth.service.feign.ProfileServiceClient;
import stackover.auth.service.model.Account;
import stackover.auth.service.model.Role;
import stackover.auth.service.security.JwtTokenProvider;
import stackover.auth.service.service.AccountService;
import stackover.auth.service.service.AuthService;
import stackover.auth.service.service.RefreshTokenService;
import stackover.auth.service.service.RoleService;
import stackover.auth.service.util.enums.RoleNumEnum;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ProfileServiceClient profileServiceClient;
    private final ProfileMapper profileMapper;
    private final RoleService roleService;
    private final AccountService accountService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void signup(SignupRequestDto signupRequestDto) {
        Account account = new Account();
        account.setEmail(signupRequestDto.email());
        account.setPassword(passwordEncoder.encode(signupRequestDto.password()));
        account.setEnabled(true);

        RoleNumEnum roleEnum = RoleNumEnum.valueOf(signupRequestDto.roleName().toUpperCase());
        Role role = roleService.findByName(roleEnum)
                .orElseThrow(() -> new IllegalStateException("Роль не найдена: " + roleEnum));
        account.setRole(role);

        account = accountService.save(account);
        log.info("Создан новый аккаунт: {}", account.getEmail());

        try {
            var profilePostDto = profileMapper.toProfilePostDto(signupRequestDto, account.getId());
            profileServiceClient.createProfileByPostDto(profilePostDto);
            log.info("Профиль успешно создан для аккаунта: {}", account.getEmail());
        } catch (Exception e) {
            log.error("❌ Ошибка при создании профиля, откатываем регистрацию аккаунта: {}", e.getMessage());
            accountService.delete(account);
            throw new RuntimeException("Ошибка при регистрации: не удалось создать профиль");
        }
    }

    @Override
    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        Account account = accountService.findByEmail(loginRequestDto.email())
                .orElseThrow(() -> {
                    log.warn("Попытка входа с несуществующим email: {}", loginRequestDto.email());
                    return new RuntimeException("Неверный логин или пароль");
                });

        if (!passwordEncoder.matches(loginRequestDto.password(), account.getPassword())) {
            log.warn("Неверный пароль для аккаунта: {}", account.getEmail());
            throw new RuntimeException("Неверный логин или пароль");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(account);
        String refreshToken = refreshTokenService.generateAndSaveRefreshToken(account);

        log.info("Успешный вход пользователя: {}", account.getEmail());

        return new AuthResponseDto(
                account.getId(),
                account.getEmail(),
                accessToken,
                jwtTokenProvider.getExpirationTime(),
                refreshToken
        );
    }

    @Override
    public RefreshTokenResponseDto refreshToken(String refreshToken) {
        try {
            Account account = refreshTokenService.validateRefreshToken(refreshToken);
            String newAccessToken = refreshTokenService.refreshAccessToken(account);
            String newRefreshToken = refreshTokenService.generateAndSaveRefreshToken(account);

            refreshTokenService.revokeRefreshToken(refreshToken);
            log.info("Токены обновлены для аккаунта ID: {}", account.getId());

            return new RefreshTokenResponseDto(newAccessToken, newRefreshToken,false);
        } catch (InvalidJwtException e) {
            log.error("Ошибка обновления токена: {}", e.getMessage());
            throw e;
        }
    }
}