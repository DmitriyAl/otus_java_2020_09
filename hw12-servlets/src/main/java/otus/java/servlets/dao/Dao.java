package otus.java.servlets.dao;

import otus.java.servlets.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    ID insertOrUpdate(T entity);

    SessionManager getSessionManager();
}