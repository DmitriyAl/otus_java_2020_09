package otus.java.tomcat.dao;

import otus.java.tomcat.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    ID insertOrUpdate(T entity);

    SessionManager getSessionManager();
}