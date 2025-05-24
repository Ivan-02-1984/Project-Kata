package stackover.auth.service.service;

import stackover.auth.service.model.Role;
import stackover.auth.service.util.enums.RoleNumEnum;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(RoleNumEnum name);
}
