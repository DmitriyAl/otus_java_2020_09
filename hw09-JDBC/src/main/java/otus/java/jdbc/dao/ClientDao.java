package otus.java.jdbc.dao;

import otus.java.jdbc.executor.DbExecutor;
import otus.java.jdbc.mapper.JdbcMapper;
import otus.java.jdbc.mapper.JdbcMapperImpl;
import otus.java.jdbc.model.Client;
import otus.java.jdbc.sessionmanager.SessionManager;
import otus.java.jdbc.sessionmanager.SessionManagerJdbc;

import java.util.List;
import java.util.Optional;

public class ClientDao implements Dao<Client> {
    private final JdbcMapper<Client> jdbcMapper;
    private final SessionManagerJdbc sessionManager;

    public ClientDao(SessionManagerJdbc sessionManager, DbExecutor<Client> dbExecutor) {
        this.jdbcMapper = new JdbcMapperImpl<>(sessionManager, dbExecutor, Client.class);
        this.sessionManager = sessionManager;
    }

    @Override
    public List<Client> findAll() {
        return jdbcMapper.findAll();
    }

    @Override
    public Optional<Client> findById(Object id) {
        return jdbcMapper.findById(id);
    }

    @Override
    public long insert(Client client) {
        return jdbcMapper.insert(client);
    }

    @Override
    public long update(Client client) {
        return jdbcMapper.update(client);
    }

    @Override
    public long insertOrUpdate(Client client) {
        return jdbcMapper.insertOrUpdate(client);
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
