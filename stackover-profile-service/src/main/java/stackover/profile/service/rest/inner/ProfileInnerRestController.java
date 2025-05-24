package stackover.profile.service.rest.inner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stackover.profile.service.dto.ProfilePostDto;
import stackover.profile.service.dto.ProfileResponseDto;
import stackover.profile.service.service.ProfileService;

@RestController
@RequestMapping("/api/inner/profile")
public class ProfileInnerRestController {
    private final ProfileService profileService;

    public ProfileInnerRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<Void> createProfile(@RequestBody ProfilePostDto profilePostDto) {
        profileService.saveProfileByPostDto(profilePostDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfileById(@RequestParam Long accountId) {
        return ResponseEntity.ok().body(profileService.getProfileResponseDtoById(accountId));
    }

}
