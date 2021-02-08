package otus.java.tomcat.service;

import org.springframework.stereotype.Service;
import otus.java.tomcat.cache.Cache;
import otus.java.tomcat.dao.Dao;
import otus.java.tomcat.dto.UserDto;
import otus.java.tomcat.model.User;

import java.util.List;
import java.util.Random;

@Service
public class UserService extends DbServiceImpl<UserDto, User, Long> {
    public UserService(Dao<User, Long> dao, Cache<Long, UserDto> cache) {
        super(dao, cache);
    }

    public UserDto getRandom() {
        final List<UserDto> all = getAll();
        return all.get(new Random().nextInt(all.size()));
    }
}