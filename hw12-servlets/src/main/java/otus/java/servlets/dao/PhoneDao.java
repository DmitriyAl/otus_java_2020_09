package otus.java.servlets.dao;

import org.hibernate.Session;
import otus.java.servlets.exception.ClientDaoException;
import otus.java.servlets.model.Phone;
import otus.java.servlets.sessionmanager.DatabaseSessionHibernate;
import otus.java.servlets.sessionmanager.SessionManagerHibernate;

import java.util.UUID;

public class PhoneDao extends AbstractDao<Phone, UUID> {
    public PhoneDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, Phone.class);
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
            return entity.getId();
        } catch (Exception e) {
            throw new ClientDaoException(e);
        }
    }
}