package stackover.resource.service.repository.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stackover.resource.service.dto.responce.QuestionResponseDto;
import stackover.resource.service.entity.question.Question;

import java.util.Optional;

@Repository
public interface QuestionDtoRepository extends JpaRepository<Question, Long> {
    @Query("""
            SELECT NEW stackover.resource.service.dto.responce.QuestionResponseDto(
                q.id,
                q.title,
                u.id,
                u.fullName,
                u.imageLink,
                q.description,
                (SELECT COUNT(qv) FROM QuestionViewed qv WHERE qv.question = q),
                (SELECT COALESCE(SUM(r.count), 0) FROM Reputation r WHERE r.author = u),
                (SELECT COUNT(a) FROM Answer a WHERE a.question = q AND a.isDeleted = false),
                COALESCE((SELECT SUM(CASE WHEN vq.voteTypeQuestion = 'UP' THEN 1 ELSE -1 END) 
                         FROM VoteQuestion vq WHERE vq.question = q), 0),
                q.persistDateTime,
                q.lastUpdateDateTime,
                (SELECT COUNT(vq) FROM VoteQuestion vq WHERE vq.question = q),
                (SELECT vq.voteTypeQuestion FROM VoteQuestion vq 
                 WHERE vq.question = q AND vq.user.id = :accountId)
            )
            FROM Question q
            JOIN q.user u
            WHERE q.id = :questionId
            AND q.isDeleted = false
            """)
    Optional<QuestionResponseDto> getQuestionDtoByQuestionIdAndUserId(@Param("questionId") Long questionId,
                                                                      @Param("accountId") Long accountId);
}
