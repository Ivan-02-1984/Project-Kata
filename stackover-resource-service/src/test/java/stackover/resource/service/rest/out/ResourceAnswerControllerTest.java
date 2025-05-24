package stackover.resource.service.rest.out;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;
import stackover.resource.service.SpringSimpleContextTest;
import stackover.resource.service.dto.responce.ProfileResponseDto;
import stackover.resource.service.entity.user.reputation.Reputation;
import stackover.resource.service.entity.user.reputation.ReputationType;
import stackover.resource.service.feign.AuthServiceClient;
import stackover.resource.service.feign.ProfileServiceClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResourceAnswerControllerTest extends SpringSimpleContextTest {

    @MockBean private AuthServiceClient authServiceClient;
    @MockBean private ProfileServiceClient profileServiceClient;

    @Test
    @Sql(scripts = {"/scripts/out/ResourceAnswerControllerTest/successfulDownVote/BeforeTest.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/out/ResourceAnswerControllerTest/successfulDownVote/AfterTest.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void successfulDownVote_ShouldDecreaseVotesAndReputation() throws Exception {
        Long answerId = 100L;
        Long voterId = 101L;
        long penalty = 5;

        mockUserProfile(voterId, "voter@test.com", "Голосующий", "voter");

        // Before
        assertFalse(reputationRecordExists(voterId, answerId), "Не должно быть записи репутации до голосования");
        long initialDownVotes = getDownVotesCount();
        long initialReputation = getAuthorReputation();

        // Action
        performDownVoteRequest(voterId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(greaterThanOrEqualTo(0)));

        // After
        assertEquals(initialDownVotes + 1, getDownVotesCount(), "Количество downvote должно увеличиться на 1");
        assertEquals(initialReputation - penalty, getAuthorReputation(), "Репутация должна уменьшиться на 5");

        Optional<Reputation> reputation = getReputationRecord(voterId, answerId);
        assertTrue(reputation.isPresent(), "Должна появиться запись репутации");
        assertEquals(-5, reputation.get().getCount(), "Значение репутации должно быть -5");
        assertEquals(ReputationType.VOTE_ANSWER, reputation.get().getType(), "Тип должен быть VOTE_ANSWER");
    }

    @Test
    @Sql(scripts = {"/scripts/out/ResourceAnswerControllerTest/changeVoteToDown/BeforeTest.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/out/ResourceAnswerControllerTest/changeVoteToDown/AfterTest.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changeVoteFromUpToDown_ShouldUpdateVotesAndReputation() throws Exception {
        Long answerId = 100L;
        Long voterId = 101L;
        long penalty = 5;
        long reward = 10;

        mockUserProfile(voterId, "voter@test.com", "Голосующий", "voter");

        // Before - проверяем, что есть upvote и соответствующая запись репутации
        assertTrue(getUpVotesCount() > 0, "Должен существовать upvote перед тестом");
        assertTrue(reputationRecordExists(voterId, answerId), "Должна существовать запись репутации для upvote");

        long initialDownVotes = getDownVotesCount();
        long initialUpVotes = getUpVotesCount();
        long initialReputation = getAuthorReputation();

        // Action
        performDownVoteRequest(voterId).andExpect(status().isOk());

        // After
        assertEquals(initialDownVotes + 1, getDownVotesCount(), "Downvotes +1");
        assertEquals(initialUpVotes - 1, getUpVotesCount(), "Upvotes -1");
        assertEquals(initialReputation - reward - penalty,
                getAuthorReputation(), "Репутация должна измениться на -15 (10+5)");
    }

    @Test
    @Sql(scripts = {"/scripts/out/ResourceAnswerControllerTest/voteForOwnAnswer/BeforeTest.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/out/ResourceAnswerControllerTest/voteForOwnAnswer/AfterTest.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void voteForOwnAnswer_ShouldReturnBadRequest() throws Exception {
        Long answerId = 100L;
        Long authorId = 100L;

        mockUserProfile(authorId, "author@test.com", "Автор", "author");

        // Before
        long initialDownVotes = getDownVotesCount();
        long initialReputation = getAuthorReputation();

        // Action & Assert
        performDownVoteRequest(authorId)
                .andExpect(status().isBadRequest());

        // After
        assertEquals(initialDownVotes, getDownVotesCount(), "Количество голосов не должно измениться");
        assertEquals(initialReputation, getAuthorReputation(), "Репутация не должна измениться");
        assertFalse(reputationRecordExists(authorId, answerId), "Не должно быть записи репутации");
    }

    @Test
    @Sql(scripts = {"/scripts/out/ResourceAnswerControllerTest/answerNotExist/BeforeTest.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/out/ResourceAnswerControllerTest/answerNotExist/AfterTest.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void answerNotExist_ShouldReturnNotFound() throws Exception {
        Long voterId = 101L;

        mockUserProfile(voterId, "voter@test.com", "Голосующий", "voter");
        mockMvc.perform(post("/api/user/question/100/answer/999/downVote")
                        .param("accountId", voterId.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void invalidParameters_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/user/question/-1/answer/-2/downVote")
                        .param("accountId", "-3"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = {"/scripts/out/ResourceAnswerControllerTest/repeatDownVote/BeforeTest.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/out/ResourceAnswerControllerTest/repeatDownVote/AfterTest.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void repeatDownVote_ShouldNotChangeAnything() throws Exception {
        Long answerId = 100L;
        Long voterId = 101L;

        mockUserProfile(voterId, "voter@test.com", "Голосующий", "voter");

        // Before - проверяем, что уже есть downvote
        assertTrue(getDownVotesCount() > 0, "Должен существовать downvote перед тестом");
        assertTrue(reputationRecordExists(voterId, answerId), "Должна существовать запись репутации для downvote");

        long initialDownVotes = getDownVotesCount();
        long initialReputation = getAuthorReputation();

        // Action
        performDownVoteRequest(voterId).andExpect(status().isOk());

        // After
        assertEquals(initialDownVotes, getDownVotesCount(), "Количество голосов не должно измениться");
        assertEquals(initialReputation, getAuthorReputation(), "Репутация не должна измениться");
    }

    @Test
    @Sql(scripts = {"/scripts/out/ResourceAnswerControllerTest/voteForOwnAnswer/BeforeTest.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/out/ResourceAnswerControllerTest/voteForOwnAnswer/AfterTest.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void accountNotExist_ShouldReturnBadRequest() throws Exception {
        when(authServiceClient.isAccountExist(999L)).thenReturn(false);
        performDownVoteRequest(999L).andExpect(status().isBadRequest());
    }

    // Вспомогательные методы
    private ProfileResponseDto buildProfile(Long id, String email, String fullName, String nickname) {
        return new ProfileResponseDto(
                id, id, email, fullName, "Москва",
                LocalDateTime.now(), "https://example.com",
                "https://github.com/" + nickname, "https://vk.com/" + nickname,
                "О себе", "https://image.url/" + nickname, nickname, true
        );
    }

    private void mockUserProfile(Long userId, String email, String fullName, String nickname) {
        when(authServiceClient.isAccountExist(userId)).thenReturn(true);
        when(profileServiceClient.getProfileById(userId))
                .thenReturn(ResponseEntity.ok(buildProfile(userId, email, fullName, nickname)));
    }

    private long getDownVotesCount() {
        Long answerId = 100L;
        return entityManager.createQuery(
                        "SELECT COUNT(v) FROM VoteAnswer v WHERE v.answer.id = :answerId AND v.voteTypeAnswer = 'DOWN'",
                        Long.class)
                .setParameter("answerId", answerId)
                .getSingleResult();
    }

    private long getUpVotesCount() {
        Long answerId = 100L;

        return entityManager.createQuery(
                        "SELECT COUNT(v) FROM VoteAnswer v WHERE v.answer.id = :answerId AND v.voteTypeAnswer = 'UP'",
                        Long.class)
                .setParameter("answerId", answerId)
                .getSingleResult();
    }

    private long getAuthorReputation() {
        Long answerId = 100L;
        Long authorId = 100L;

        return entityManager.createQuery(
                        "SELECT COALESCE(SUM(r.count), 0) FROM Reputation r " +
                                "WHERE r.author.id = :authorId AND r.answer.id = :answerId",
                        Long.class)
                .setParameter("authorId", authorId)
                .setParameter("answerId", answerId)
                .getSingleResult();
    }

    private boolean reputationRecordExists(Long senderId, Long answerId) {
        return entityManager.createQuery(
                        "SELECT COUNT(r) > 0 FROM Reputation r WHERE r.sender.id = :senderId AND r.answer.id = :answerId",
                        Boolean.class)
                .setParameter("senderId", senderId)
                .setParameter("answerId", answerId)
                .getSingleResult();
    }

    private Optional<Reputation> getReputationRecord(Long senderId, Long answerId) {
        List<Reputation> result = entityManager.createQuery(
                        "SELECT r FROM Reputation r WHERE r.sender.id = :senderId AND r.answer.id = :answerId",
                        Reputation.class)
                .setParameter("senderId", senderId)
                .setParameter("answerId", answerId)
                .getResultList();

        return result.stream().findFirst();
    }

    private ResultActions performDownVoteRequest(Long accountId) throws Exception {
        Long questionId = 100L;
        Long answerId = 100L;

        return mockMvc.perform(post("/api/user/question/{qId}/answer/{aId}/downVote", questionId, answerId)
                        .param("accountId", accountId.toString()))
                .andDo(print());
    }
}