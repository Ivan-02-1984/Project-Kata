package stackover.auth.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stackover.auth.service.dto.account.AccountResponseDto;
import stackover.auth.service.exception.EntityNotFoundException;
import stackover.auth.service.model.Account;
import stackover.auth.service.repository.AccountRepository;
import stackover.auth.service.service.AccountService;
import stackover.auth.service.util.enums.RoleNumEnum;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public AccountResponseDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + id));

        return new AccountResponseDto(
                account.getId(),
                account.getEmail(),
                account.getRole().getName(),  // Получаем RoleNumEnum из сущности Role
                account.getEnabled()
        );
    }

    @Override
    public boolean existsByIdAndRole(Long id, RoleNumEnum role) {
        return accountRepository.existsByIdAndRoleName(id, role);
    }

    @Override
    public Boolean existsById(Long id) {
        return accountRepository.existsById(id);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void delete(Account account) {
        accountRepository.delete(account);
    }
}