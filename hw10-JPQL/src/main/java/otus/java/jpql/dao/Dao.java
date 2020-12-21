package otus.java.jpql.dao;

import otus.java.jpql.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    ID insertOrUpdate(T user);

    SessionManager getSessionManager();
}