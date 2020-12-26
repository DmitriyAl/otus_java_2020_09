package otus.java.cache.dao;

import otus.java.cache.cache.Cache;
import otus.java.cache.model.Address;
import otus.java.cache.sessionmanager.SessionManagerHibernate;

public class AddressDao extends AbstractNumericIdDao<Address, Long>{
    public AddressDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, Address.class);
    }

    public AddressDao(SessionManagerHibernate sessionManager, Cache<Long, Address> cache) {
        super(sessionManager, cache, Address.class);
    }
}