package otus.java.messages.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.java.messages.sessionmanager.DatabaseSessionHibernate;
import otus.java.messages.sessionmanager.SessionManager;
import otus.java.messages.sessionmanager.SessionManagerHibernate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T, ID> implements Dao<T, ID> {
    protected final SessionManagerHibernate sessionManager;
    protected final Class<T> clazz;

    private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);

    public AbstractDao(SessionManagerHibernate sessionManager, Class<T> clazz) {
        this.sessionManager = sessionManager;
        this.clazz = clazz;
    }

    @Override
    public List<T> findAll() {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            String query = String.format("select o from %s as o", clazz.getSimpleName());
            return currentSession.getHibernateSession().createQuery(query, clazz)
                    .getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<T> findById(ID id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(clazz, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}