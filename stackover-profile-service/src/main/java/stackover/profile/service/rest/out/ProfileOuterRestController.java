package stackover.profile.service.rest.out;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stackover.profile.service.dto.ProfileRequestDto;
import stackover.profile.service.dto.ProfileResponseDto;
import stackover.profile.service.entity.Profile;
import stackover.profile.service.rest.out.response.Response;
import stackover.profile.service.service.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileOuterRestController {
    private final ProfileService profileService;

    public ProfileOuterRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<Profile> getProfileByAuthorization(@RequestHeader(name = "account_id") Long accountId) {
        return ResponseEntity.ok(profileService.findProfileByAccountId(accountId));
    }

    @PutMapping
    public Response<ProfileResponseDto> updateProfileByIdAndProfileRequestDto(@RequestHeader(name = "account_id") Long accountId,
                                                                              @RequestBody ProfileRequestDto requestDto) {
        return Response.ok(profileService.updateProfileByIdAndProfileRequestDto(accountId, requestDto));
    }
}
