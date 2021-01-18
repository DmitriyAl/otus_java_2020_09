package otus.java.di.exception;

public class BeanInitializationException extends BaseException {
    public BeanInitializationException(String message) {
        super(message);
    }

    public BeanInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}