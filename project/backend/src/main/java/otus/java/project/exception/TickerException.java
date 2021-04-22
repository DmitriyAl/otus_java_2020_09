package otus.java.project.exception;

public class TickerException extends CoreException{
    public TickerException(String message) {
        super(message);
    }

    public TickerException(String message, Throwable cause) {
        super(message, cause);
    }
}
