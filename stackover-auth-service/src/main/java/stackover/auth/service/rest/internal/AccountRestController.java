package stackover.auth.service.rest.internal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import stackover.auth.service.dto.account.AccountResponseDto;
import stackover.auth.service.service.AccountService;
import stackover.auth.service.util.enums.RoleNumEnum;

@RestController
@RequestMapping("/api/internal/account")
@RequiredArgsConstructor
@Tag(name = "Внутренний API аккаунта", description = "Методы для внутренних сервисов (resource, profile и др.)")
public class AccountRestController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить аккаунт по ID")
    public AccountResponseDto getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/{id}/exists")
    @Operation(summary = "Проверить существование аккаунта по ID")
    public Boolean checkExistById(@PathVariable Long id) {
        return accountService.existsById(id);
    }

    @GetMapping("/{id}/exists/with-role")
    @Operation(summary = "Проверить существование аккаунта с ролью")
    public Boolean checkExistByIdAndRole(@PathVariable Long id, @RequestParam RoleNumEnum role) {
        return accountService.existsByIdAndRole(id, role);
    }
}

