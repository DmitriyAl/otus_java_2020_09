package otus.java.jdbc.service;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.java.jdbc.PgDataSource;
import otus.java.jdbc.dao.Dao;
import otus.java.jdbc.executor.DbExecutorImpl;
import otus.java.jdbc.mapper.JdbcMapperImpl;
import otus.java.jdbc.model.Client;
import otus.java.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

class DbServiceClientImplTest {
    DBService<Client, Long> serviceClient;

    @BeforeEach
    void init() {
        var dataSource = new PgDataSource();
        flywayMigrations(dataSource);
        var sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutorImpl<Client, Long> dbExecutor = new DbExecutorImpl<>();
        Dao<Client, Long> dao = new JdbcMapperImpl<>(sessionManager, dbExecutor, Client.class);
        serviceClient = new DbServiceImpl<>(dao);
    }

    @Test
    void insertClient() {
        assertThat(serviceClient.getAll()).hasSize(0);
        Client ben = new Client("Ben", 15);
        long benId = serviceClient.save(ben);
        assertThat(serviceClient.getAll()).hasSize(1).containsOnly(ben);
        assertThat(serviceClient.getById(benId)).isNotEmpty().contains(ben);
        Client mark = new Client("Mark", 21);
        long markId = serviceClient.save(mark);
        assertThat(serviceClient.getAll()).hasSize(2).containsOnly(ben, mark);
        assertThat(serviceClient.getById(markId)).isNotEmpty().contains(mark);
    }

    @Test
    void updateClient() {
        assertThat(serviceClient.getAll()).hasSize(0);
        Client ben = new Client("Ben", 15);
        long benId = serviceClient.save(ben);
        assertThat(serviceClient.getAll()).hasSize(1).containsOnly(ben);
        assertThat(serviceClient.getById(benId)).isNotEmpty().contains(ben);
        Client mark = new Client(benId,"Mark", 21);
        long markId = serviceClient.save(mark);
        assertThat(markId).isEqualTo(benId);
        assertThat(serviceClient.getAll()).hasSize(1).containsOnly(mark);
        assertThat(serviceClient.getById(markId)).isNotEmpty().contains(mark);
        assertThat(serviceClient.getById(benId)).isNotEmpty().contains(mark);
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