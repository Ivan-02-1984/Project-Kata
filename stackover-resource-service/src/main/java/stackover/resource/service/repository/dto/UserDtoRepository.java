package stackover.resource.service.repository.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.dto.responce.UserResponseDto;

import java.util.Optional;

@Repository
public interface UserDtoRepository extends JpaRepository<User, Long> {
    @Query("""
    SELECT new stackover.resource.service.dto.responce.UserResponseDto (
        u.id,
        u.fullName,
        u.imageLink,
        u.city,
        CAST(COALESCE(SUM(r.count), 0) AS long), 
        u.persistDateTime,
        CAST(COALESCE(
            COALESCE(SUM(CASE 
                WHEN va.voteTypeAnswer = stackover.resource.service.entity.question.answer.VoteTypeAnswer.UP THEN 1 
                WHEN va.voteTypeAnswer = stackover.resource.service.entity.question.answer.VoteTypeAnswer.DOWN THEN 0 - 1 
                ELSE 0 END), 0)
            +
            COALESCE(SUM(CASE 
                WHEN vq.voteTypeQuestion = stackover.resource.service.entity.question.VoteTypeQuestion.UP THEN 1 
                WHEN vq.voteTypeQuestion = stackover.resource.service.entity.question.VoteTypeQuestion.DOWN THEN 0 - 1 
                ELSE 0 END), 0),
            0
        ) AS long))
    FROM User u
    LEFT JOIN Reputation r ON r.author.id = u.id OR r.sender.id = u.id
    LEFT JOIN VoteAnswer va ON va.user.id = u.id
    LEFT JOIN VoteQuestion vq ON vq.user.id = u.id
    WHERE u.id = :userId
    GROUP BY u.id, u.fullName, u.imageLink, u.city, u.persistDateTime
""")

    Optional<UserResponseDto> getUserResponseDtoByUserId(@Param("userId") Long userId);

}

