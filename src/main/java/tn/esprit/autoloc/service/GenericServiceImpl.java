package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

import java.util.List;

@RequiredArgsConstructor
public abstract class GenericServiceImpl<T, K> implements IGenericService<T, K> {

    protected final JpaRepository<T, K> repository;

    protected abstract String getEntityName();

    protected abstract K getId(T entity);

    // Copie les champs modifiables de source vers target (entite existante)
    protected abstract void setFields(T source, T target);

    @Override
    public T create(T entity) {
        if (getId(entity) != null) {
            throw new IllegalArgumentException("L'identifiant de " + getEntityName() + " doit etre vide a la creation");
        }
        if (!check(entity, true)) {
            throw new IllegalArgumentException(getEntityName() + " invalide");
        }
        return repository.save(entity);
    }

    @Override
    public T findById(K id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getEntityName(), String.valueOf(id)));
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T update(K id, T entity) {
        T existing = findById(id);
        if (!check(entity, false)) {
            throw new IllegalArgumentException(getEntityName() + " invalide");
        }
        setFields(entity, existing);
        return repository.save(existing);
    }

    @Override
    public void deleteById(K id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(getEntityName(), String.valueOf(id));
        }
        repository.deleteById(id);
    }
}
