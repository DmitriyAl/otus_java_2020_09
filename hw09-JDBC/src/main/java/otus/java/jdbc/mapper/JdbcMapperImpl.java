package otus.java.jdbc.mapper;

import otus.java.jdbc.exception.DaoException;
import otus.java.jdbc.executor.DbExecutor;
import otus.java.jdbc.sessionmanager.SessionManager;
import otus.java.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T, ID> implements JdbcMapper<T, ID> {
    private final EntityClassMetaData<T> classMetaData;
    private final EntitySQLMetaData sqlMetaData;
    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T, ID> dbExecutor;

    public JdbcMapperImpl(SessionManagerJdbc sessionManager, DbExecutor<T, ID> dbExecutor, Class<T> clazz) {
        this.classMetaData = new EntityClassMetaDataImpl<>(clazz);
        this.sqlMetaData = new EntitySQLMetaDataImpl(classMetaData);
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public ID insertOrUpdate(T objectData) {
        try {
            List<Object> params;
            String sql;
            if (getParam(objectData, classMetaData.getIdField()) == null) {
                params = getParams(objectData, classMetaData.getFieldsWithoutId());
                sql = sqlMetaData.getInsertSql();
            } else {
                params = getParams(objectData, classMetaData.getAllFields());
                sql = sqlMetaData.getInsertUpdateSql();
            }
            return dbExecutor.executeInsertOrUpdate(getConnection(), sql, params);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    private List<Object> getParams(T object, List<Field> fields) {
        List<Object> params = new ArrayList<>();
        for (Field field : fields) {
            Object value = getParam(object, field);
            params.add(value);
        }
        return params;
    }

    private Object getParam(T object, Field field) {
        field.setAccessible(true);
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public Optional<T> findById(ID id) {
        try {
            return dbExecutor.executeSelect(getConnection(), sqlMetaData.getSelectByIdSql(), id, this::handleSelect);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    private T handleSelect(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return fillInObject(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        try {
            return dbExecutor.executeSelectAll(getConnection(), sqlMetaData.getSelectAllSql(), this::handleSelectAll);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }

    private List<T> handleSelectAll(ResultSet resultSet) {
        List<T> all = new ArrayList<>();
        try {
            while (resultSet.next()) {
                all.add(fillInObject(resultSet));
            }
            return all;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private T fillInObject(ResultSet resultSet) throws Exception {
        T result = classMetaData.getConstructor().newInstance();
        List<Field> fields = classMetaData.getAllFields();
        for (int i = 0; i < fields.size(); i++) {
            initializeField(result, fields.get(i), resultSet, i + 1);
        }
        return result;
    }

    private void initializeField(T result, Field field, ResultSet resultSet, int index) throws Exception {
        Object value = resultSet.getObject(index);
        field.setAccessible(true);
        field.set(result, value);
    }

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
