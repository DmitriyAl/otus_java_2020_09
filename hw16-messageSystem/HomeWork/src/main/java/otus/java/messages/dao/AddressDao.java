package otus.java.messages.dao;

import org.springframework.stereotype.Repository;
import otus.java.messages.model.Address;
import otus.java.messages.sessionmanager.SessionManagerHibernate;

@Repository
public class AddressDao extends AbstractNumericIdDao<Address, Long> {
    public AddressDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, Address.class);
    }
}