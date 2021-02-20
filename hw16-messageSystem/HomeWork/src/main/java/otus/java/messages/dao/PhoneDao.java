package otus.java.messages.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import otus.java.messages.exception.ClientDaoException;
import otus.java.messages.model.Phone;
import otus.java.messages.sessionmanager.DatabaseSessionHibernate;
import otus.java.messages.sessionmanager.SessionManagerHibernate;

import java.util.UUID;

@Repository
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