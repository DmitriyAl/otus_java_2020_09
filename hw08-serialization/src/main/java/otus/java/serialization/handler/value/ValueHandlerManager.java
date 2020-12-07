package otus.java.serialization.handler.value;

import otus.java.serialization.model.FieldType;
import otus.java.serialization.processor.ObjectProcessor;

import java.util.HashMap;
import java.util.Map;

public class ValueHandlerManager {
    private final Map<FieldType, ValueHandler> handlers;

    public ValueHandlerManager(ObjectProcessor processor) {
        this.handlers = new HashMap<>();
        handlers.put(FieldType.STRING, new DefaultValueHandler());
        handlers.put(FieldType.NUMBER_WRAPPER, new NumberValueHandler());
        handlers.put(FieldType.BIG_DECIMAL, new BigDecimalValueHandler());
        handlers.put(FieldType.BIG_INTEGER, new BigIntegerValueHandler());
        handlers.put(FieldType.BOOLEAN, new BooleanValueHandler());
        handlers.put(FieldType.ARRAY, new ArrayValueHandler(this));
        handlers.put(FieldType.COLLECTION, new CollectionValueHandler(this));
        handlers.put(FieldType.OBJECT, new ObjectValueHandler(processor));
    }

    public ValueHandler getHandler(FieldType type) {
        return handlers.getOrDefault(type, handlers.get(FieldType.STRING));
    }
}