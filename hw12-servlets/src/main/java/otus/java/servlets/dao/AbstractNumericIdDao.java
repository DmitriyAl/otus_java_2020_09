package otus.java.servlets.dao;

import org.hibernate.Session;
import otus.java.servlets.exception.ClientDaoException;
import otus.java.servlets.model.IntegerId;
import otus.java.servlets.sessionmanager.DatabaseSessionHibernate;
import otus.java.servlets.sessionmanager.SessionManagerHibernate;

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