package otus.java.todo;

import otus.java.todo.handler.ComplexProcessor;
import otus.java.todo.listener.StoringMessageListener;
import otus.java.todo.processor.Processor;
import otus.java.todo.processor.ReplaceProcessor;

import java.util.Collections;
import java.util.List;

public class HomeWork {
//todo разного рода сценарии а также тест на процессор с ошибкой можно найти в TestScenarios
    public static void main(String[] args) {
        List<Processor> processors = Collections.singletonList(new ReplaceProcessor());
        var complexProcessor = new ComplexProcessor(processors, ex -> System.out.println(ex.getMessage()));
        var messageStorage = new MessageStorage();
        complexProcessor.addListener(new StoringMessageListener(messageStorage));
        var field13 = new ObjectForMessage();
        field13.setData(Collections.singletonList("field13"));
        var message = new Message.Builder().field11("field11").field12("field12").field13(field13).build();
        complexProcessor.handle(message);
        System.out.println(messageStorage.getHistory());
    }
}