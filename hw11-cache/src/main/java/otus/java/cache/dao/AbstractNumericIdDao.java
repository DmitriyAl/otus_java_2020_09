package otus.java.cache.dao;

import org.hibernate.Session;
import otus.java.cache.exception.ClientDaoException;
import otus.java.cache.model.IntegerId;
import otus.java.cache.sessionmanager.DatabaseSessionHibernate;
import otus.java.cache.sessionmanager.SessionManagerHibernate;

public abstract class AbstractNumericIdDao<T extends IntegerId<ID>, ID extends Long> extends AbstractDao<T, ID> {

    public AbstractNumericIdDao(SessionManagerHibernate sessionManager, Class<T> clazz) {
        super(sessionManager, clazz);
    }

    @Override
    public ID insertOrUpdate(T entity) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (entity.getId() != null && entity.getId().longValue() > 0) {
                hibernateSession.merge(entity);
            } else {
                hibernateSession.persist(entity);
                hibernateSession.flush();
            }
            return entity.getId();
        } catch (Exception e) {
            throw new ClientDaoException(e);
        }
    }
}