package otus.java.jdbc.exception;

public class RuntimeCoreException extends RuntimeException {
    public RuntimeCoreException(String message) {
        super(message);
    }

    public RuntimeCoreException(Throwable cause) {
        super(cause);
    }
}
