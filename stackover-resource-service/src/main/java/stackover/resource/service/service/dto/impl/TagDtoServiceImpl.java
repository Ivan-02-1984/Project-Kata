package stackover.resource.service.service.dto.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import stackover.resource.service.dto.responce.TagResponseDto;
import stackover.resource.service.repository.dto.TagDtoRepository;
import stackover.resource.service.service.dto.TagDtoService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagDtoServiceImpl implements TagDtoService {

    private final TagDtoRepository tagDtoRepository;

    @Override
    public List<TagResponseDto> getTagsByQuestionId(Long questionId) {

        List<TagResponseDto> tags = tagDtoRepository.findTagsByQuestionId(questionId);

        if (tags == null || tags.isEmpty()) {
            log.warn("Для вопроса с ID {} не найдено ни одного тега", questionId);
            return Collections.emptyList();
        }
        return tags;
    }
}
