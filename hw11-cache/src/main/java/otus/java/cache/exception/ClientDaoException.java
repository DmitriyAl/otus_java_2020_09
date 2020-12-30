package otus.java.cache.exception;

public class ClientDaoException extends RuntimeException {
    public ClientDaoException(Exception ex) {
        super(ex);
    }
}