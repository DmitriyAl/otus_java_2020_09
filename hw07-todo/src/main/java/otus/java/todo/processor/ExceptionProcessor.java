package otus.java.todo.processor;

import otus.java.todo.Message;
import otus.java.todo.exception.EvenSecondsException;

public class ExceptionProcessor implements Processor {
    private final DateProvider dateProvider;

    public ExceptionProcessor(DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    @Override
    public Message process(Message message) {
        int seconds = dateProvider.getDate().getSecond();
        if (seconds % 2 == 0) {
            throw new EvenSecondsException(seconds);
        }
        return message;
    }
}