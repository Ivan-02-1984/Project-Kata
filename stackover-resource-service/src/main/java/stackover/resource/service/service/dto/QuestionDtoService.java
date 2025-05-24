package stackover.resource.service.service.dto;

import stackover.resource.service.dto.responce.QuestionResponseDto;

public interface QuestionDtoService {

    QuestionResponseDto getQuestionWithUserCheck(Long questionId, Long accountId);

}
