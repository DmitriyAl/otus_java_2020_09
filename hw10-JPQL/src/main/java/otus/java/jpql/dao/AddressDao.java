package otus.java.jpql.dao;

import otus.java.jpql.model.Address;
import otus.java.jpql.sessionmanager.SessionManagerHibernate;

public class AddressDao extends AbstractNumericIdDao<Address, Long>{
    public AddressDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, Address.class);
    }
}