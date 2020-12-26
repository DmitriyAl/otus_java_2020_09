package otus.java.cache.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import otus.java.cache.cache.*;
import otus.java.cache.dao.Dao;
import otus.java.cache.model.Address;
import otus.java.cache.model.Phone;
import otus.java.cache.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService implements DbService<User, Long> {
    private final Dao<User, Long> dao;
    private final Cache<Long, User> cache;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(Dao<User, Long> dao, Cache<Long, User> cache) {
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public Long save(User entity) {
        return null;
    }

    @Override
    public Optional<User> getById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}