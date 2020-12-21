package otus.java.jpql.base;

import org.hibernate.SessionFactory;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import otus.java.jpql.model.Address;
import otus.java.jpql.model.Phone;
import otus.java.jpql.model.User;
import otus.java.jpql.util.HibernateUtils;
import otus.java.jpql.util.MigrationsExecutorFlyway;

public abstract class AbstractHibernateTest {
    protected SessionFactory sessionFactory;

    protected void setUp() {
        var migrationsExecutor = new MigrationsExecutorFlyway();
        migrationsExecutor.cleanDb();
        migrationsExecutor.executeMigrations();

        sessionFactory = HibernateUtils.buildSessionFactory(User.class, Address.class, Phone.class);
    }

    protected void tearDown() {
        sessionFactory.close();
    }

    protected EntityStatistics getUsageStatistics() {
        Statistics stats = sessionFactory.getStatistics();
        return stats.getEntityStatistics(User.class.getName());
    }
}