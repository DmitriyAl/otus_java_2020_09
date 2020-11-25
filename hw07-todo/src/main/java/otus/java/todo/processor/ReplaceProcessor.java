package otus.java.todo.processor;

import otus.java.todo.Message;

public class ReplaceProcessor implements Processor {
    @Override
    public Message process(Message message) {
        return message.toBuilder().field11(message.getField12()).field12(message.getField11()).build();
    }
}