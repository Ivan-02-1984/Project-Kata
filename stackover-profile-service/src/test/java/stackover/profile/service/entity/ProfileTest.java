package stackover.profile.service.entity;

import stackover.profile.service.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfileTest {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private static Profile invalidProfileWithAccountId;
    private static Profile invalidProfileWithEmptyName;
    private static Profile validProfile;

    @BeforeAll
    static void init() {
        invalidProfileWithAccountId = Profile.builder()
                .accountId(0L)
                .email("test@mail.com")
                .fullName("test")
                .city("city")
                .persistDateTime(null)
                .linkSite("url.linkSite")
                .linkGitHub("url.linkGitHub")
                .linkVk("url.linkVk")
                .about("about")
                .imageLink("url.imageLink")
                .nickname("nickname")
                .build();

        invalidProfileWithEmptyName = Profile.builder()
                .accountId(1L)
                .email("test@mail.com")
                .fullName("")
                .city("city")
                .persistDateTime(null)
                .linkSite("url.linkSite")
                .linkGitHub("url.linkGitHub")
                .linkVk("url.linkVk")
                .about("about")
                .imageLink("url.imageLink")
                .nickname("nickname")
                .build();

        validProfile = Profile.builder()
                .accountId(1L)
                .email("test@mail.com")
                .fullName("test")
                .city("city")
                .persistDateTime(null)
                .linkSite("url.linkSite")
                .linkGitHub("url.linkGitHub")
                .linkVk("url.linkVk")
                .about("about")
                .imageLink("url.imageLink")
                .nickname("nickname")
                .build();

    }

    @Test
    void whenProfileIdIncorrect_thenThrowsException() {
        assertThrows(ConstraintViolationException.class, () -> {
            testEntityManager.persist(invalidProfileWithAccountId);
            testEntityManager.flush();
        });
    }

    @Test
    void whenProfileNameIncorrect_thenThrowsException() {
        assertThrows(ConstraintViolationException.class, () -> {
            testEntityManager.persist(invalidProfileWithEmptyName);
            testEntityManager.flush();
        });
    }

    @Test
    void whenProfileIsCorrect() {
        profileRepository.saveAndFlush(validProfile);

        Profile profile = profileRepository.findByAccountId(1L);
        assertEquals(validProfile, profile, "Save correct profile failed");

        // UPDATE BLOCK
        String testData = "newTest";
        profile.setFullName(testData);
        profileRepository.saveAndFlush(profile);

        Profile newProfile = profileRepository.findByAccountId(1L);
        assertEquals(testData, newProfile.getFullName(), "Update profile test failed");

        // REMOVE BLOCK
        profileRepository.deleteById(newProfile.getId());
        assertEquals(0, profileRepository.count(), "delete profile test failed");
    }
}