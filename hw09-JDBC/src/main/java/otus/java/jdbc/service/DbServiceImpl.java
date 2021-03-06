package otus.java.jdbc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.java.jdbc.dao.Dao;
import otus.java.jdbc.exception.DbServiceException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DbServiceImpl<T, ID> implements DBService<T, ID> {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceImpl.class);

    private final Dao<T, ID> dao;

    public DbServiceImpl(Dao<T, ID> dao) {
        this.dao = dao;
    }

    @Override
    public ID save(T entity) {
        try (var sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var clientId = dao.insertOrUpdate(entity);
                sessionManager.commitSession();

                logger.info("created entity: {}", clientId);
                return clientId;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<T> getById(ID id) {
        try (var sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<T> optional = dao.findById(id);

                logger.info("entity: {}", optional.orElse(null));
                return optional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public List<T> getAll() {
        try (var sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                List<T> entities = dao.findAll();
                logger.info("entities: {}", entities);
                return entities;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Collections.emptyList();
        }
    }
}
