package otus.java.todo.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import otus.java.todo.Message;
import otus.java.todo.MessagePair;
import otus.java.todo.MessageStorage;
import otus.java.todo.ObjectForMessage;
import otus.java.todo.listener.StoringMessageListener;
import otus.java.todo.processor.ExceptionProcessor;
import otus.java.todo.processor.Processor;
import otus.java.todo.processor.ReplaceProcessor;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestScenarios {
    @Test
    @DisplayName("Тестируем выбрасывание исключения в четное количество секунд")
    public void evenSeconds() {
        var message = new Message.Builder().field9("field9").build();
        var complexProcessor = new ComplexProcessor(Collections.singletonList(new ExceptionProcessor()), this::handleEvenSecondsException);
        complexProcessor.handle(message);
    }

    private void handleEvenSecondsException(Exception exception) {
        String message = exception.getMessage();
        int seconds = Integer.parseInt(message.replaceAll("[^0-9]+", "").trim());
        assertThat(seconds % 2).isEqualTo(0);
    }

    @Test
    @DisplayName("Тестируем, что сохраненные сообщения не модифицируемы")
    public void notModifiableHistoryTest() {
        List<Processor> processors = Collections.singletonList(new ReplaceProcessor());
        var complexProcessor = new ComplexProcessor(processors, ex -> System.out.println(ex.getMessage()));
        var messageStorage = new MessageStorage();
        complexProcessor.addListener(new StoringMessageListener(messageStorage));
        var field13 = new ObjectForMessage();
        field13.setData(Collections.singletonList("field13"));
        var message = new Message.Builder().field11("field11").field12("field12").field13(field13).build();
        complexProcessor.handle(message);
        MessagePair messagePair = messageStorage.getHistory().get(0);
        messagePair.getInitial().toBuilder().field1("broken").build();
        assertThat(messageStorage.getHistory().get(0).getInitial().getField1()).isNull();
    }
}
