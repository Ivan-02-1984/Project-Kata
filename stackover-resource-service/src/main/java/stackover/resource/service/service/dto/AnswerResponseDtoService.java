package stackover.resource.service.service.dto;

import stackover.resource.service.dto.response.AnswerResponseDto;
import stackover.resource.service.exception.CheckedAccountExistsException;

import java.util.List;

public interface AnswerResponseDtoService {
    List<AnswerResponseDto> getAnswersDtoByQuestionId(Long questionId, Long accountId)throws CheckedAccountExistsException;
}
