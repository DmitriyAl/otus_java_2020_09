package otus.java.jdbc.dao;


import otus.java.jdbc.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    List<T> findAll();

    Optional<T> findById(Object id);

    long insert(T client);

    long update(T client);

    long insertOrUpdate(T user);

    SessionManager getSessionManager();
}
