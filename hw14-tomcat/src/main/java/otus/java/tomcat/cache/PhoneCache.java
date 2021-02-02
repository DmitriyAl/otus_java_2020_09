package otus.java.tomcat.cache;

import org.springframework.stereotype.Component;
import otus.java.tomcat.dto.PhoneDto;

import java.util.UUID;

@Component
public class PhoneCache extends MyWeakCache<UUID, PhoneDto> {
}