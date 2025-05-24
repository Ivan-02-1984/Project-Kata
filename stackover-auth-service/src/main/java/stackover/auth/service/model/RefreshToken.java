package stackover.auth.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RefreshToken {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    private Account account;
    private String token;
    private Instant expiryDate;

}
