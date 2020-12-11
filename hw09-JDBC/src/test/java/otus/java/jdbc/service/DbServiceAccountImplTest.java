package otus.java.jdbc.service;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.java.jdbc.PgDataSource;
import otus.java.jdbc.dao.Dao;
import otus.java.jdbc.executor.DbExecutorImpl;
import otus.java.jdbc.mapper.JdbcMapperImpl;
import otus.java.jdbc.model.Account;
import otus.java.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DbServiceAccountImplTest {
    DBService<Account, UUID> serviceAccount;

    @BeforeEach
    void init() {
        var dataSource = new PgDataSource();
        flywayMigrations(dataSource);
        var sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutorImpl<Account, UUID> dbExecutor = new DbExecutorImpl<>();
        Dao<Account, UUID> dao = new JdbcMapperImpl<>(sessionManager, dbExecutor, Account.class);
        serviceAccount = new DbServiceImpl<>(dao);
    }

    @Test
    void insertClient() {
        assertThat(serviceAccount.getAll()).hasSize(0);
        Account first = new Account(UUID.randomUUID(), "common", 1.2f);
        UUID firstId = serviceAccount.save(first);
        assertThat(serviceAccount.getAll()).hasSize(1).containsOnly(first);
        assertThat(serviceAccount.getById(firstId)).isNotEmpty().contains(first);
        Account second = new Account(UUID.randomUUID(), "private", 2.7f);
        UUID secondId = serviceAccount.save(second);
        assertThat(serviceAccount.getAll()).hasSize(2).containsOnly(first, second);
        assertThat(serviceAccount.getById(secondId)).isNotEmpty().contains(second);
    }

    @Test
    void updateClient() {
        assertThat(serviceAccount.getAll()).hasSize(0);
        Account first = new Account(UUID.randomUUID(), "common", 1.2f);
        UUID firstId = serviceAccount.save(first);
        assertThat(serviceAccount.getAll()).hasSize(1).containsOnly(first);
        assertThat(serviceAccount.getById(firstId)).isNotEmpty().contains(first);
        Account second = new Account(firstId, "private", 2.7f);
        UUID secondId = serviceAccount.save(second);
        assertThat(secondId).isEqualTo(firstId);
        assertThat(serviceAccount.getAll()).hasSize(1).containsOnly(second);
        assertThat(serviceAccount.getById(secondId)).isNotEmpty().contains(second);
        assertThat(serviceAccount.getById(firstId)).isNotEmpty().contains(second);
    }

    private static void flywayMigrations(DataSource dataSource) {
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.clean();
        flyway.migrate();
    }
}
