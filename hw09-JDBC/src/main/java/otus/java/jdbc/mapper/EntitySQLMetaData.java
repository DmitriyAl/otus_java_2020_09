package otus.java.jdbc.mapper;

/**
 * Создает SQL - запросы
 */
public interface EntitySQLMetaData {
    String getSelectAllSql();

    String getSelectByIdSql();

    String getInsertSqlWithoutId();

    String getInsertSqlWithId();

    String getUpdateSql();
}
