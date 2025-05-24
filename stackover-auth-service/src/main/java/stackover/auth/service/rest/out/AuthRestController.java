package stackover.auth.service.rest.out;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import stackover.auth.service.dto.request.LoginRequestDto;
import stackover.auth.service.dto.request.SignupRequestDto;
import stackover.auth.service.dto.response.AuthResponseDto;
import stackover.auth.service.dto.token.RefreshTokenResponseDto;
import stackover.auth.service.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "Регистрация нового аккаунта")
    public void signup(@RequestBody SignupRequestDto signupRequestDto) {
        authService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Аутентификация пользователя")
    public AuthResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Обновить токен")
    public RefreshTokenResponseDto refresh(@RequestBody String token) {
        return authService.refreshToken(token);
    }
}