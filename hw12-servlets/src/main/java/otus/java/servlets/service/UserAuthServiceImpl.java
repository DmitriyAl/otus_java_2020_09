package otus.java.servlets.service;

import otus.java.servlets.dao.UserDao;
import otus.java.servlets.sessionmanager.SessionManager;

public class UserAuthServiceImpl implements UserAuthService {

    private final UserDao userDao;

    public UserAuthServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean authenticate(String login, String password) {
        final SessionManager sessionManager = userDao.getSessionManager();
        sessionManager.beginSession();
        try {
            final Boolean isAuthenticated = userDao.findByLogin(login)
                    .map(user -> user.getPassword().equals(password))
                    .orElse(false);
            sessionManager.commitSession();
            return isAuthenticated;
        } catch (Exception e) {
            sessionManager.rollbackSession();
            return false;
        }
    }
}