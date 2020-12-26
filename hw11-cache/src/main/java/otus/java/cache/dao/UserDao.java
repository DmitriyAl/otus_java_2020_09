package otus.java.cache.dao;

import otus.java.cache.cache.Cache;
import otus.java.cache.model.User;
import otus.java.cache.sessionmanager.SessionManagerHibernate;

public class UserDao extends AbstractNumericIdDao<User, Long> {
    public UserDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, User.class);
    }

    public UserDao(SessionManagerHibernate sessionManager, Cache<Long, User> cache) {
        super(sessionManager, cache, User.class);
    }
}