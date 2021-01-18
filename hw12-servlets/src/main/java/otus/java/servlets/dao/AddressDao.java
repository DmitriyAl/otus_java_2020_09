package otus.java.servlets.dao;

import otus.java.servlets.model.Address;
import otus.java.servlets.sessionmanager.SessionManagerHibernate;

public class AddressDao extends AbstractNumericIdDao<Address, Long>{
    public AddressDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, Address.class);
    }
}