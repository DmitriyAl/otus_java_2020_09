package otus.java.jdbc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.java.jdbc.dao.Dao;
import otus.java.jdbc.model.Client;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClientDbService implements DBService<Client> {
    private static final Logger logger = LoggerFactory.getLogger(ClientDbService.class);

    private final Dao<Client> dao;

    public ClientDbService(Dao<Client> dao) {
        this.dao = dao;
    }

    @Override
    public long save(Client client) {
        try (var sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var clientId = dao.insertOrUpdate(client);
                sessionManager.commitSession();

                logger.info("created client: {}", clientId);
                return clientId;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<Client> getById(Object id) {
        try (var sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Client> clientOptional = dao.findById(id);

                logger.info("client: {}", clientOptional.orElse(null));
                return clientOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Client> getAll() {
        try (var sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                List<Client> clients = dao.findAll();
                logger.info("clients: {}", clients);
                return clients;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Collections.emptyList();
        }
    }
}
