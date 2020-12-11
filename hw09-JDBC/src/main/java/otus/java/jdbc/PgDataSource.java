package otus.java.jdbc;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class PgDataSource implements DataSource {
    private static final String URL = "jdbc:postgresql://localhost:5432/db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(USER, PASSWORD);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        var connection = DriverManager.getConnection(URL, username, password);
        connection.setAutoCommit(false);
        return connection;
    }

    @Override
    public PrintWriter getLogWriter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLogWriter(PrintWriter out) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLoginTimeout() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setLoginTimeout(int seconds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Logger getParentLogger() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        throw new UnsupportedOperationException();
    }
}
