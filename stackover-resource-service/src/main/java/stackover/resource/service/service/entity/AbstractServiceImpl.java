
package stackover.resource.service.service.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public abstract class AbstractServiceImpl<T, PK> implements AbstractService<T, PK> {

    protected abstract JpaRepository<T, PK> getRepository();

    @Override
    public Optional<T> findById(PK id) {
        return getRepository().findById(id);
    }

    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T update(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public void deleteById(PK id) {
        getRepository().deleteById(id);
    }

    @Override
    public boolean existsById(PK id) {
        return getRepository().existsById(id);
    }
}