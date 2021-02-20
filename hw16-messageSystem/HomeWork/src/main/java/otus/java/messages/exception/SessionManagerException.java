package otus.java.messages.exception;


public class SessionManagerException extends RuntimeException {
    public SessionManagerException(String msg) {
        super(msg);
    }

    public SessionManagerException(Exception ex) {
        super(ex);
    }
}