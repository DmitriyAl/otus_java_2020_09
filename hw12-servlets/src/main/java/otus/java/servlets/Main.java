package otus.java.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.SessionFactory;
import otus.java.servlets.dao.UserDao;
import otus.java.servlets.model.Address;
import otus.java.servlets.model.Phone;
import otus.java.servlets.model.User;
import otus.java.servlets.server.UsersWebServer;
import otus.java.servlets.server.UsersWebServerWithFilterBasedSecurity;
import otus.java.servlets.service.*;
import otus.java.servlets.sessionmanager.SessionManagerHibernate;
import otus.java.servlets.util.HibernateUtils;
import otus.java.servlets.util.MigrationsExecutorFlyway;

public class Main {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/web/templates/";

    public static void main(String[] args) throws Exception {
        var migrationsExecutor = new MigrationsExecutorFlyway();
        migrationsExecutor.cleanDb();
        migrationsExecutor.executeMigrations();

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(User.class, Address.class, Phone.class);
        UserDao userDao = new UserDao(new SessionManagerHibernate(sessionFactory));
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userDao);
        DbService<User, Long> userService = new DbServiceImpl<>(userDao);

        UsersWebServer usersWebServer = new UsersWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, userService, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}