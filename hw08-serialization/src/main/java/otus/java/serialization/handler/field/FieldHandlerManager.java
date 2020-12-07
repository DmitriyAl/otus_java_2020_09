package otus.java.serialization.handler.field;

import otus.java.serialization.handler.value.ValueHandlerManager;
import otus.java.serialization.model.FieldType;
import otus.java.serialization.processor.ObjectProcessor;

import java.util.HashMap;
import java.util.Map;

public class FieldHandlerManager {
    private final Map<FieldType, FieldHandler> handlers;

    public FieldHandlerManager(ObjectProcessor processor, ValueHandlerManager valueHandlerManager) {
        this.handlers = new HashMap<>();
        handlers.put(FieldType.STRING, new DefaultFieldHandler());
        handlers.put(FieldType.NUMBER_WRAPPER, new NumberFieldHandler());
        handlers.put(FieldType.BIG_DECIMAL, new BigDecimalFieldHandler());
        handlers.put(FieldType.BIG_INTEGER, new BigIntegerFieldHandler());
        handlers.put(FieldType.BOOLEAN, new BooleanFieldHandler());
        handlers.put(FieldType.COLLECTION, new CollectionFieldHandler(valueHandlerManager));
        handlers.put(FieldType.ARRAY, new ArrayFieldHandler(valueHandlerManager));
        handlers.put(FieldType.OBJECT, new ObjectFieldHandler(processor));
    }

    public FieldHandler getHandler(FieldType type) {
        return handlers.getOrDefault(type, handlers.get(FieldType.STRING));
    }
}