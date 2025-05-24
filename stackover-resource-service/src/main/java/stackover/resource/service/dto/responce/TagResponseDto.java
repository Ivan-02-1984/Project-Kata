package stackover.resource.service.dto.responce;

import io.swagger.v3.oas.annotations.media.Schema;

public record TagResponseDto(
        @Schema(description = "id тэга")
        Long id,
        @Schema(description = "название тэга")
        String name,
        @Schema(description = "описание тэга")
        String description
) {
}
