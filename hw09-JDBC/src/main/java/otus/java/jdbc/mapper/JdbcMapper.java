package otus.java.jdbc.mapper;

import otus.java.jdbc.model.Client;

import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 * @param <T>
 */
public interface JdbcMapper<T> {
    long insert(T objectData);

    long update(T objectData);

    long insertOrUpdate(T objectData);

    Optional<T> findById(Object id);

    List<T> findAll();
}
