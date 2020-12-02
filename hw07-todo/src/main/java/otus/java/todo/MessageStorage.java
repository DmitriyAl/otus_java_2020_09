package otus.java.todo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MessageStorage {
    private final List<MessagePair> storage;

    public MessageStorage() {
        this.storage = new ArrayList<>();
    }

    public void addItem(MessagePair messagePair) {
        storage.add(messagePair);
    }

    public List<MessagePair> getHistory() {
        return storage.stream().map(MessagePair::copy).collect(Collectors.toUnmodifiableList());
    }
}