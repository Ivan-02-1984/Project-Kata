package stackover.auth.service.service;

import stackover.auth.service.dto.request.LoginRequestDto;
import stackover.auth.service.dto.request.SignupRequestDto;
import stackover.auth.service.dto.response.AuthResponseDto;
import stackover.auth.service.dto.token.RefreshTokenResponseDto;

public interface AuthService {

    void signup(SignupRequestDto signupRequestDto);

    AuthResponseDto login(LoginRequestDto loginRequestDto);

    RefreshTokenResponseDto refreshToken(String refreshToken);
}

