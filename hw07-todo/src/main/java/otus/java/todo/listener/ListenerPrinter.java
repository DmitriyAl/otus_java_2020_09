package otus.java.todo.listener;

import otus.java.todo.Message;

public class ListenerPrinter implements Listener {

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        var logString = String.format("oldMsg:%s, newMsg:%s", oldMsg, newMsg);
        System.out.println(logString);
    }
}