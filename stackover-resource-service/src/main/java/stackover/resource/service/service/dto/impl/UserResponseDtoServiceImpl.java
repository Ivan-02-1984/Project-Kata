package stackover.resource.service.service.dto.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stackover.resource.service.feign.AuthServiceClient;
import stackover.resource.service.feign.ProfileServiceClient;
import stackover.resource.service.repository.dto.UserDtoRepository;
import stackover.resource.service.service.dto.UserResponseDtoService;
import stackover.resource.service.dto.responce.UserResponseDto;

import java.util.Objects;
import java.util.Optional;


/*
TODO:
- Реализовать сервис проверки наличия пользователя с определенным id в stackover-auth-service
*/

@Service
@RequiredArgsConstructor
public class UserResponseDtoServiceImpl implements UserResponseDtoService {

    private final UserDtoRepository userDtoRepository;
    private final AuthServiceClient authServiceClient;
    private final ProfileServiceClient profileServiceClient;


    @Override
    public UserResponseDto getUserResponseDtoByUserId(Long userId) {
        Optional<UserResponseDto> userResponseDtoOptional = userDtoRepository.getUserResponseDtoByUserId(userId);

        if (userResponseDtoOptional.isPresent() /* && authServiceClient.isAccountExist(userId) */) {
            UserResponseDto userResponseDto = userResponseDtoOptional.get();
            return new UserResponseDto(
                    userResponseDto.id(),
                    Objects.requireNonNull(profileServiceClient.getProfileById(userId).getBody()).email(),
                    userResponseDto.fullName(),
                    userResponseDto.linkImage(),
                    userResponseDto.city(),
                    userResponseDto.reputation(),
                    userResponseDto.registrationDate(),
                    userResponseDto.votes()
            );
        } else {
            return new UserResponseDto();
        }

    }
}
