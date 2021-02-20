package otus.java.messages.cache;

import org.springframework.stereotype.Component;
import otus.java.messages.dto.UserDto;

@Component
public class UserCache extends MyWeakCache<Long, UserDto> {
}