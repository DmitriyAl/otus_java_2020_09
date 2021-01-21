package otus.java.tomcat.dao;

import otus.java.tomcat.model.User;
import otus.java.tomcat.sessionmanager.SessionManagerHibernate;

public class UserDao extends AbstractNumericIdDao<User, Long> {
    public UserDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, User.class);
    }
}