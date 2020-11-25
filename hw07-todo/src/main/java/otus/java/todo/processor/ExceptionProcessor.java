package otus.java.todo.processor;

import otus.java.todo.Message;
import otus.java.todo.exception.EvenSecondsException;

import java.util.Calendar;

public class ExceptionProcessor implements Processor {
    @Override
    public Message process(Message message) {
        int seconds = Calendar.getInstance().get(Calendar.SECOND);
        if (seconds % 2 == 0) {
            throw new EvenSecondsException(seconds);
        }
        return message;
    }
}