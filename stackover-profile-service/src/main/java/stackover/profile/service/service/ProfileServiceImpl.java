package stackover.profile.service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stackover.profile.service.dto.AccountResponseDto;
import stackover.profile.service.dto.ProfilePostDto;
import stackover.profile.service.dto.ProfileRequestDto;
import stackover.profile.service.dto.ProfileResponseDto;
import stackover.profile.service.dto.converter.ProfileResponseDtoConverter;
import stackover.profile.service.entity.Profile;
import stackover.profile.service.feign.AuthServiceFeignClient;
import stackover.profile.service.repository.ProfileRepository;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final AuthServiceFeignClient authServiceFeignClient;

    private final ProfileRepository profileRepository;
    private final ProfileResponseDtoConverter profileResponseDtoConverter;

    public ProfileServiceImpl(ProfileRepository profileRepository, AuthServiceFeignClient authServiceFeignClient,
                              ProfileResponseDtoConverter profileResponseDtoConverter) {
        this.profileRepository = profileRepository;
        this.authServiceFeignClient = authServiceFeignClient;
        this.profileResponseDtoConverter = profileResponseDtoConverter;
    }

    @Override
    @Transactional
    public void saveProfileByPostDto(ProfilePostDto profilePostDto) {
        Profile profile = Profile.builder()
                .accountId(profilePostDto.accountId())
                .email(profilePostDto.email())
                .fullName(profilePostDto.fullName())
                .city(profilePostDto.city())
                .persistDateTime(profilePostDto.persistDateTime())
                .linkSite(profilePostDto.linkSite())
                .linkGitHub(profilePostDto.linkGitHub())
                .about(profilePostDto.about())
                .imageLink(profilePostDto.imageLink())
                .nickname(profilePostDto.nickname())
                .build();

        profileRepository.saveAndFlush(profile);
    }

    @Override
    public Profile findProfileByAccountId(Long accountId) {
        return profileRepository.findByAccountId(accountId);
    }

    @Override
    public ProfileResponseDto getProfileResponseDtoById(Long accountId) {
        return getProfileResponseDtoByProfile(profileRepository.findByAccountId(accountId));
    }

    @Override
    public boolean existByAccountId(Long accountId) {
        return profileRepository.existsByAccountId(accountId);
    }

    @Override
    public ProfileResponseDto updateProfileByIdAndProfileRequestDto(Long accountId, ProfileRequestDto requestDto) {
        Profile profile = profileRepository.findByAccountId(accountId);
        profile.setEmail(requestDto.email());
        profile.setFullName(requestDto.fullName());
        profile.setCity(requestDto.city());
        profile.setLinkSite(requestDto.linkSite());
        profile.setLinkGitHub(requestDto.linkGitHub());
        profile.setLinkVk(requestDto.linkVk());
        profile.setAbout(requestDto.about());
        profile.setImageLink(requestDto.imageLink());
        profile.setNickname(requestDto.nickname());
        profileRepository.saveAndFlush(profile);

        return getProfileResponseDtoByProfile(profile);
    }

    private ProfileResponseDto getProfileResponseDtoByProfile(Profile profile) {
        ProfileResponseDto profileResponseDto = profileResponseDtoConverter.toDto(profile);

        AccountResponseDto accountResponseDto = authServiceFeignClient.getAccountById(profile.getAccountId()).getBody();

        profileResponseDto.setEmail(accountResponseDto.email());
        profileResponseDto.setRole(accountResponseDto.role());
        profileResponseDto.setEnabled(accountResponseDto.enabled());

        return profileResponseDto;
    }
}
