package stackover.resource.service.service.dto;

import stackover.resource.service.dto.responce.TagResponseDto;

import java.util.List;

public interface TagDtoService {

    List<TagResponseDto> getTagsByQuestionId(Long questionId);
}
