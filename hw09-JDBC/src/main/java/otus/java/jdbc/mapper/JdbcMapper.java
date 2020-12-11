package otus.java.jdbc.mapper;

import otus.java.jdbc.dao.Dao;

/**
 * Сохратяет объект в базу, читает объект из базы
 * @param <T>
 */
public interface JdbcMapper<T, ID> extends Dao<T, ID> {

}
