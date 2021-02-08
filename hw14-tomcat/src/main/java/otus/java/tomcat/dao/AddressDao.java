package otus.java.tomcat.dao;

import org.springframework.stereotype.Repository;
import otus.java.tomcat.model.Address;
import otus.java.tomcat.sessionmanager.SessionManagerHibernate;

@Repository
public class AddressDao extends AbstractNumericIdDao<Address, Long> {
    public AddressDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, Address.class);
    }
}