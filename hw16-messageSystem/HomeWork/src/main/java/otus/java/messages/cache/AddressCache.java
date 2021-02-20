package otus.java.messages.cache;

import org.springframework.stereotype.Component;
import otus.java.messages.dto.AddressDto;

@Component
public class AddressCache extends MyWeakCache<Long, AddressDto> {
}