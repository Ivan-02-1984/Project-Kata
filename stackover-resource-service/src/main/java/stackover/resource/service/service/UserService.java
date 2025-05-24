package stackover.resource.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stackover.resource.service.entity.user.User;
import stackover.resource.service.repository.UserRepository;
import stackover.resource.service.service.entity.AbstractServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO не использовать класс - удалить
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService extends AbstractServiceImpl<User, Long> {

    private final UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }


}