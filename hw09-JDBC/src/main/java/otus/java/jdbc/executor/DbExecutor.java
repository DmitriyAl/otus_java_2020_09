package otus.java.jdbc.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface DbExecutor<T, ID> {

    ID executeInsert(Connection connection, String sql, List<Object> params) throws SQLException;

    Optional<T> executeSelect(Connection connection, String sql, Object id, Function<ResultSet, T> rsHandler) throws SQLException;

    List<T> executeSelectAll(Connection connection, String sql, Function<ResultSet, List<T>> rsHandler) throws SQLException;
}
