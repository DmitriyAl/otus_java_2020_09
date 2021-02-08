package otus.java.tomcat.cache;

import org.springframework.stereotype.Component;
import otus.java.tomcat.dto.AddressDto;

@Component
public class AddressCache extends MyWeakCache<Long, AddressDto> {
}