package stackover.resource.service.repository.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stackover.resource.service.dto.responce.TagResponseDto;
import stackover.resource.service.entity.question.Tag;

import java.util.List;

@Repository
public interface TagDtoRepository extends JpaRepository<Tag, Long> {

    @Query("""
            SELECT NEW stackover.resource.service.dto.responce.TagResponseDto(
                t.id, t.name, t.description)
            FROM Question q JOIN q.tags t
            WHERE q.id = :questionId
            """)
    List<TagResponseDto> findTagsByQuestionId(@Param("questionId") Long questionId);
}
