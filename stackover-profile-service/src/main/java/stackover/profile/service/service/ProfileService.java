package stackover.profile.service.service;

import stackover.profile.service.dto.ProfilePostDto;
import stackover.profile.service.dto.ProfileRequestDto;
import stackover.profile.service.dto.ProfileResponseDto;
import stackover.profile.service.entity.Profile;

public interface ProfileService {
    void saveProfileByPostDto(ProfilePostDto profilePostDto);

    Profile findProfileByAccountId(Long accountId);

    ProfileResponseDto getProfileResponseDtoById(Long accountId);

    boolean existByAccountId(Long accountId);

    ProfileResponseDto updateProfileByIdAndProfileRequestDto(Long accountId, ProfileRequestDto requestDto);

}
