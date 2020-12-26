package otus.java.cache.service;

import java.util.List;
import java.util.Optional;

public interface DbService<T, ID> {

    ID save(T entity);

    Optional<T> getById(ID id);

    List<T> getAll();
}