package otus.java.cache.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.java.cache.cache.Cache;
import otus.java.cache.sessionmanager.DatabaseSessionHibernate;
import otus.java.cache.sessionmanager.SessionManager;
import otus.java.cache.sessionmanager.SessionManagerHibernate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T, ID> implements Dao<T, ID> {
    protected final SessionManagerHibernate sessionManager;
    protected final Cache<ID, T> cache;
    protected final Class<T> clazz;

    private static final Logger logger = LoggerFactory.getLogger(AbstractDao.class);

    public AbstractDao(SessionManagerHibernate sessionManager, Class<T> clazz) {
        this.sessionManager = sessionManager;
        this.cache = null;
        this.clazz = clazz;
    }

    public AbstractDao(SessionManagerHibernate sessionManager, Cache<ID, T> cache, Class<T> clazz) {
        this.sessionManager = sessionManager;
        this.cache = cache;
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
        final Optional<T> cachedEntity = returnCachedValue(id);
        if (cachedEntity.isPresent()) return cachedEntity;
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            final Optional<T> optional = Optional.ofNullable(currentSession.getHibernateSession().find(clazz, id));
            optional.ifPresent(entity -> cacheEntity(id, entity));
            return optional;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private void cacheEntity(ID id, T entity) {
        if (cache != null) {
            cache.put(id, entity);
        }
    }

    private Optional<T> returnCachedValue(ID id) {
        if (cache != null) {
            final T cachedEntity = cache.get(id);
            if (cachedEntity != null) {
                return Optional.of(cachedEntity);
            }
        }
        return Optional.empty();
    }

    protected void removeFromCache(ID id) {
        if (cache != null) {
            cache.remove(id);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}