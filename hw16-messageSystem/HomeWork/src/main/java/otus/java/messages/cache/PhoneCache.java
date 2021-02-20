package otus.java.messages.cache;

import org.springframework.stereotype.Component;
import otus.java.messages.dto.PhoneDto;

import java.util.UUID;

@Component
public class PhoneCache extends MyWeakCache<UUID, PhoneDto> {
}