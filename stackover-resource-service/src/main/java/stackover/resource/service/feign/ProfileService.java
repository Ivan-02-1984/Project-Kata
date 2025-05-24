package stackover.resource.service.feign;

import org.springframework.http.ResponseEntity;
import stackover.resource.service.dto.request.ProfileDto;

//TODO Удалить
public interface ProfileService {
    ResponseEntity<ProfileDto> getProfileByAuthorization(Long accountId);
}
