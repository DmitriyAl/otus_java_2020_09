package otus.java.messages.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.java.messages.cache.Cache;
import otus.java.messages.dao.Dao;
import otus.java.messages.exception.DbServiceException;
import otus.java.messages.model.HasDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DbServiceImpl<DTO, ENTITY extends HasDto<DTO>, ID> implements DbService<DTO, ENTITY, ID> {
    private final Dao<ENTITY, ID> dao;
    private final Cache<ID, DTO> cache;
    private static final Logger logger = LoggerFactory.getLogger(DbServiceImpl.class);

    public DbServiceImpl(Dao<ENTITY, ID> dao, Cache<ID, DTO> cache) {
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public ID save(ENTITY entity) {
        try (var sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                var id = dao.insertOrUpdate(entity);
                removeFromCache(id);
                sessionManager.commitSession();

                logger.info("created entity: {}", id);
                return id;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    private void removeFromCache(ID id) {
        if (cache != null) {
            cache.remove(id);
        }
    }

    @Override
    public DTO getById(ID id) {
        final DTO fromCache = getFromCache(id);
        if (fromCache != null) {
            return fromCache;
        }
        try (var sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<ENTITY> optional = dao.findById(id);
                if (optional.isEmpty()) {
                    return null;
                }
                final ENTITY entity = optional.get();
                logger.info("entity: {}", entity);
                final DTO dto = entity.toDto();
                cacheEntity(id, dto);
                return dto;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return null;
        }
    }

    private DTO getFromCache(ID id) {
        if (cache != null) {
            return cache.get(id);
        }
        return null;
    }

    private void cacheEntity(ID id, DTO entity) {
        if (cache != null) {
            cache.put(id, entity);
        }
    }

    @Override
    public List<DTO> getAll() {
        try (var sessionManager = dao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                List<ENTITY> entities = dao.findAll();
                logger.info("entities: {}", entities);
                return entities.stream().map(HasDto::toDto).collect(Collectors.toList());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Collections.emptyList();
        }
    }
}