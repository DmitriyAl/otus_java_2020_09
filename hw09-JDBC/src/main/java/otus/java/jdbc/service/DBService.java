package otus.java.jdbc.service;

import java.util.List;
import java.util.Optional;

public interface DBService<T> {

    long save(T client);

    Optional<T> getById(Object id);

    List<T> getAll();
}
