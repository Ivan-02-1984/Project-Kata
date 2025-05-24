package stackover.profile.service.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true, updatable = false)
    @Min(value = 1, message = "incorrect accountId")
    private Long accountId;

    @Column
    @NotBlank
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String fullName;

    @Column
    private String city;

    @Column(name = "persist_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime persistDateTime;

    @Column(name = "link_site")
    private String linkSite;

    @Column(name = "link_github")
    private String linkGitHub;

    @Column(name = "link_vk")
    private String linkVk;

    @Column
    private String about;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "last_redaction_date", nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdateDateTime;

    @Column
    private String nickname;

    @Builder
    public Profile(Long accountId, String email, String fullName, String city, LocalDateTime persistDateTime,
                   String linkSite, String linkGitHub, String linkVk, String about, String imageLink, String nickname) {
        this.accountId = accountId;
        this.email = email;
        this.fullName = fullName;
        this.city = city;
        this.persistDateTime = persistDateTime;
        this.linkSite = linkSite;
        this.linkGitHub = linkGitHub;
        this.linkVk = linkVk;
        this.about = about;
        this.imageLink = imageLink;
        this.nickname = nickname;
    }

}
