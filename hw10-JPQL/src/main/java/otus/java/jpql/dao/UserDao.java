package otus.java.jpql.dao;

import otus.java.jpql.model.User;
import otus.java.jpql.sessionmanager.SessionManagerHibernate;

public class UserDao extends AbstractNumericIdDao<User, Long> {
    public UserDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, User.class);
    }
}