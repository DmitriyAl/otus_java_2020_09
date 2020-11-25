package otus.java.todo;

public class MessagePair {
    private final Message initial;
    private final Message updated;

    public MessagePair(Message initial, Message updated) {
        this.initial = initial.getCopy();
        this.updated = updated.getCopy();
    }

    private MessagePair(MessagePair source) {
        this.initial = source.initial.getCopy();
        this.updated = source.updated.getCopy();
    }

    public Message getInitial() {
        return initial.getCopy();
    }

    public Message getUpdated() {
        return updated.getCopy();
    }

    public MessagePair copy() {
        return new MessagePair(this);
    }

    @Override
    public String toString() {
        return "MessagePair{" +
                "initial=" + initial +
                ", updated=" + updated +
                '}';
    }
}