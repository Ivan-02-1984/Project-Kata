package stackover.auth.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name = "account_generator", initialValue = 11799, allocationSize = 1)
    private Long id;

    @Email
    private String email;

    @NotNull
    @Column(length = 60, nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled = true;

    private String localeTag = "ru";

    @OneToOne
    @ToString.Exclude
    private Role role;

    @Builder
    public Account(String email, String password, String localeTag, Role role) {
        this.email = email;
        this.password = password;
        this.localeTag = localeTag;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(email, account.email) && Objects.equals(password, account.password) && Objects.equals(enabled, account.enabled) && Objects.equals(localeTag, account.localeTag) && Objects.equals(role, account.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, enabled, localeTag, role);
    }
}
