package otus.java.jpql.dao;

import otus.java.jpql.model.Phone;
import otus.java.jpql.sessionmanager.SessionManagerHibernate;

public class PhoneDao extends AbstractNumericIdDao<Phone, Long> {
    public PhoneDao(SessionManagerHibernate sessionManager) {
        super(sessionManager, Phone.class);
    }
}