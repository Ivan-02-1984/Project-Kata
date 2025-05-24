package stackover.resource.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import stackover.resource.service.dto.response.AnswerResponseDto;
import stackover.resource.service.entity.question.answer.Answer;

import java.util.List;

public interface AnswerDtoRepository extends JpaRepository<Answer, Long> {
    @Query("""
    SELECT new stackover.resource.service.dto.response.AnswerResponseDto(
        a.id,
        u.id,
        q.id,
        a.htmlBody,
        a.persistDateTime,
        a.isHelpful,
        a.dateAcceptTime,
        COALESCE((
            SELECT SUM(CASE WHEN va.voteTypeAnswer = 'UP' THEN 1 ELSE -1 END)
            FROM VoteAnswer va
            WHERE va.answer = a
        ), 0),
        COALESCE((
            SELECT SUM(r.count)
            FROM Reputation r
            WHERE r.author = u
        ), 0),
        u.imageLink,
        u.nickname,
        (SELECT COUNT(va2) FROM VoteAnswer va2 WHERE va2.answer = a),
        (SELECT va3.voteTypeAnswer
         FROM VoteAnswer va3
         WHERE va3.answer = a AND va3.user.id = :accountId)
    )
    FROM Answer a
    JOIN a.user u
    JOIN a.question q
    WHERE q.id = :questionId and u.id = :accountId
    """)
    List<AnswerResponseDto> getAnswersDtoByQuestionId(@Param("questionId") Long questionId,
                                                      @Param("accountId") Long accountId);
}