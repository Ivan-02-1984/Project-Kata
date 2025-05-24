package stackover.auth.service.service;

import stackover.auth.service.dto.account.AccountResponseDto;
import stackover.auth.service.model.Account;
import stackover.auth.service.util.enums.RoleNumEnum;

import javax.validation.constraints.Email;
import java.util.Optional;

public interface AccountService {

    AccountResponseDto getAccountById(Long id);

    boolean existsByIdAndRole(Long id, RoleNumEnum role);

    Boolean existsById(Long id);

    Account save(Account account);

    Optional<Account> findByEmail(@Email String email);

    void delete(Account account);

}
