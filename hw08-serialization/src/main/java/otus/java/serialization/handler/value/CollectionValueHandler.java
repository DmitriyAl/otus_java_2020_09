package otus.java.serialization.handler.value;

import otus.java.serialization.model.FieldValue;
import otus.java.serialization.util.CollectionToJsonArray;

import javax.json.JsonArrayBuilder;

public class CollectionValueHandler implements ValueHandler {
    private final ValueHandlerManager valueHandlerManager;

    public CollectionValueHandler(ValueHandlerManager valueHandlerManager) {
        this.valueHandlerManager = valueHandlerManager;
    }

    @Override
    public void handleValue(JsonArrayBuilder builder, FieldValue fieldValue) {
        builder.add(CollectionToJsonArray.convert(valueHandlerManager, fieldValue.getValue()));
    }
}