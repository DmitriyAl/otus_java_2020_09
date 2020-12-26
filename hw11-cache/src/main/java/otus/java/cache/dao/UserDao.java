package otus.java.cache.dao;

import otus.java.cache.model.User;
import otus.java.cache.sessionmanager.SessionManagerHibernate;

public class UserDao extends AbstractNumericIdDao<User, Long> {
    public UserDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, User.class);
    }
}