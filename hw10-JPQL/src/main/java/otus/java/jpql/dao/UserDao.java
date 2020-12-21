package otus.java.jpql.dao;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.java.jpql.exception.ClientDaoException;
import otus.java.jpql.model.User;
import otus.java.jpql.sessionmanager.DatabaseSessionHibernate;
import otus.java.jpql.sessionmanager.SessionManager;
import otus.java.jpql.sessionmanager.SessionManagerHibernate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User, Long> {
    private final SessionManagerHibernate sessionManager;

    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public UserDao(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public List<User> findAll() {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return currentSession.getHibernateSession().createQuery("select u from User as u", User.class)
                    .getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<User> findById(Long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            return Optional.ofNullable(currentSession.getHibernateSession().find(User.class, id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Long insertOrUpdate(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (user.getId() != null && user.getId() > 0) {
                hibernateSession.merge(user);
            } else {
                hibernateSession.persist(user);
                hibernateSession.flush();
            }
            return user.getId();
        } catch (Exception e) {
            throw new ClientDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}