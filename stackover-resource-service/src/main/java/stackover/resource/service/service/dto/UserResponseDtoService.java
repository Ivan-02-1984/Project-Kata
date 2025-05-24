package stackover.resource.service.service.dto;

import stackover.resource.service.dto.responce.UserResponseDto;

import java.util.Optional;

public interface UserResponseDtoService {
    UserResponseDto getUserResponseDtoByUserId(Long userId);

}
