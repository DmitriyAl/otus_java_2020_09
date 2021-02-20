package otus.java.messages.dao;

import org.springframework.stereotype.Repository;
import otus.java.messages.model.User;
import otus.java.messages.sessionmanager.SessionManagerHibernate;

@Repository
public class UserDao extends AbstractNumericIdDao<User, Long> {
    public UserDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, User.class);
    }
}