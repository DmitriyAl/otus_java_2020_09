package otus.java.servlets.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.java.servlets.model.User;
import otus.java.servlets.sessionmanager.DatabaseSessionHibernate;
import otus.java.servlets.sessionmanager.SessionManagerHibernate;

import java.util.Optional;

public class UserDao extends AbstractNumericIdDao<User, Long> {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public UserDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, User.class);
    }

    public Optional<User> findByLogin(String login) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            String query = String.format("select u from %s as u where u.login = '%s'", clazz.getSimpleName(), login);
            return Optional.ofNullable(currentSession.getHibernateSession().createQuery(query, clazz).getSingleResult());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }
}