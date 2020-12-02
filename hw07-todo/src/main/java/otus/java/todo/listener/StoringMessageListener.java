package otus.java.todo.listener;

import otus.java.todo.Message;
import otus.java.todo.MessagePair;
import otus.java.todo.MessageStorage;

public class StoringMessageListener implements Listener {
    private final MessageStorage storage;

    public StoringMessageListener(MessageStorage storage) {
        this.storage = storage;
    }

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        storage.addItem(new MessagePair(oldMsg, newMsg));
    }
}