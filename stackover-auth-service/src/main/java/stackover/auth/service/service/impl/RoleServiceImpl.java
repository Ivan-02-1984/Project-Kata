package stackover.auth.service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stackover.auth.service.model.Role;
import stackover.auth.service.repository.RoleRepository;
import stackover.auth.service.service.RoleService;
import stackover.auth.service.util.enums.RoleNumEnum;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(RoleNumEnum name) {
        return roleRepository.findByName(name);
    }
}
