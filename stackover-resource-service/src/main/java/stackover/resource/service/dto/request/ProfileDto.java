package stackover.resource.service.dto.request;

import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

public record ProfileDto(
        Long id,
        Long accountId,
        String email,
        String fullName,
        String city,
        LocalDateTime persistDateTime,
        String linkSite,
        String linkGitHub,
        String linkVk,
        String about,
        String imageLink,
        @UpdateTimestamp LocalDateTime lastUpdateDateTime,
        String nickname
) {}