package otus.java.tomcat.dao;

import org.springframework.stereotype.Repository;
import otus.java.tomcat.model.User;
import otus.java.tomcat.sessionmanager.SessionManagerHibernate;

@Repository
public class UserDao extends AbstractNumericIdDao<User, Long> {
    public UserDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, User.class);
    }
}