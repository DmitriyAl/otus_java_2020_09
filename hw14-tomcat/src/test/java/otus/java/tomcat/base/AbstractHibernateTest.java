package otus.java.tomcat.base;

import org.hibernate.SessionFactory;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import otus.java.tomcat.model.Address;
import otus.java.tomcat.model.Phone;
import otus.java.tomcat.model.User;
import otus.java.tomcat.sessionmanager.SessionManagerHibernate;
import otus.java.tomcat.util.HibernateUtils;
import otus.java.tomcat.util.MigrationsExecutorFlyway;

public abstract class AbstractHibernateTest {
    private SessionFactory sessionFactory;
    protected SessionManagerHibernate sessionManager;

    protected void setUp() {
        var migrationsExecutor = new MigrationsExecutorFlyway();
        migrationsExecutor.cleanDb();
        migrationsExecutor.executeMigrations();

        sessionFactory = HibernateUtils.buildSessionFactory(User.class, Address.class, Phone.class);
        sessionManager = new SessionManagerHibernate(sessionFactory);
    }

    @AfterEach
    protected void tearDown() {
        sessionFactory.close();
    }

    protected EntityStatistics getUsageStatistics() {
        Statistics stats = sessionFactory.getStatistics();
        return stats.getEntityStatistics(User.class.getName());
    }
}