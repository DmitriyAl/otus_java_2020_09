package otus.java.jdbc.mapper;

import otus.java.jdbc.dao.DaoException;
import otus.java.jdbc.executor.DbExecutor;
import otus.java.jdbc.sessionmanager.SessionManagerJdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private final EntityClassMetaData<T> classMetaData;
    private final EntitySQLMetaData sqlMetaData;
    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T> dbExecutor;

    public JdbcMapperImpl(SessionManagerJdbc sessionManager, DbExecutor<T> dbExecutor, Class<T> clazz) {
        this.classMetaData = new EntityClassMetaDataImpl<>(clazz);
        this.sqlMetaData = new EntitySQLMetaDataImpl<>(classMetaData);
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
    }

    @Override
    public long insert(T objectData) {
        try {
            return dbExecutor.executeInsert(getConnection(), sqlMetaData.getInsertSql(),
                    getParams(objectData, classMetaData.getFieldsWithoutId()));
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
    public long update(T objectData) {
        try {
            List<Object> params = getParams(objectData, classMetaData.getFieldsWithoutId());
            params.add(getParam(objectData, classMetaData.getIdField()));
            return dbExecutor.executeInsert(getConnection(), sqlMetaData.getUpdateSql(), params);
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public long insertOrUpdate(T objectData) {
        Object id = getParam(objectData, classMetaData.getIdField());
        if (id != null) {
            Optional<T> optional = findById(id);
            if (optional.isPresent()) {
                return update(objectData);
            }
        }
        return insert(objectData);
    }

    @Override
    public Optional<T> findById(Object id) {
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

    private void initializeField(T result, Field field, ResultSet resultSet, int index) throws Exception {
        Object value = resultSet.getObject(index);
        field.setAccessible(true);
        field.set(result, value);
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

    private Connection getConnection() {
        return sessionManager.getCurrentSession().getConnection();
    }
}
