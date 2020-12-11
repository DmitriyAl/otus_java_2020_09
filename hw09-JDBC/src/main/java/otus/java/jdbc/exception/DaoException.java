package otus.java.jdbc.exception;

public class DaoException extends RuntimeCoreException {
    public DaoException(Exception ex) {
        super(ex);
    }
}
