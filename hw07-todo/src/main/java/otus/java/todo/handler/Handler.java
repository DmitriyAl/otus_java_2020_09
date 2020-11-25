package otus.java.todo.handler;

import otus.java.todo.Message;
import otus.java.todo.listener.Listener;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);
    void removeListener(Listener listener);
}