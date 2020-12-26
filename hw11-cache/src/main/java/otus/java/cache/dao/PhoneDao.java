package otus.java.cache.dao;

import org.hibernate.Session;
import otus.java.cache.cache.Cache;
import otus.java.cache.exception.ClientDaoException;
import otus.java.cache.model.Phone;
import otus.java.cache.sessionmanager.DatabaseSessionHibernate;
import otus.java.cache.sessionmanager.SessionManagerHibernate;

import java.util.UUID;

public class PhoneDao extends AbstractDao<Phone, UUID> {
    public PhoneDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, Phone.class);
    }

    public PhoneDao(SessionManagerHibernate sessionManager, Cache<UUID, Phone> cache) {
        super(sessionManager, cache, Phone.class);
    }

    @Override
    public UUID insertOrUpdate(Phone entity) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (entity.getId() != null) {
                hibernateSession.merge(entity);
            } else {
                hibernateSession.persist(entity);
                hibernateSession.flush();
            }
            removeFromCache(entity.getId());
            return entity.getId();
        } catch (Exception e) {
            throw new ClientDaoException(e);
        }
    }
}