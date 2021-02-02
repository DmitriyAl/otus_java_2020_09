package otus.java.tomcat.cache;

import org.springframework.stereotype.Component;
import otus.java.tomcat.dto.UserDto;

@Component
public class UserCache extends MyWeakCache<Long, UserDto> {
}