package otus.java.todo.exception;

public class EvenSecondsException extends RuntimeException {
    public EvenSecondsException(int seconds) {
        super(String.format("The value of seconds '%d' is even", seconds));
    }
}