package otus.java.messages.base;

import org.hibernate.SessionFactory;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import otus.java.messages.model.Address;
import otus.java.messages.model.Phone;
import otus.java.messages.model.User;
import otus.java.messages.sessionmanager.SessionManagerHibernate;
import otus.java.messages.util.HibernateUtils;
import otus.java.messages.util.MigrationsExecutorFlyway;

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