package otus.java.serialization.handler.value;

import otus.java.serialization.model.FieldValue;
import otus.java.serialization.util.ArrayToJsonArray;

import javax.json.JsonArrayBuilder;

public class ArrayValueHandler implements ValueHandler {
    private final ValueHandlerManager valueHandlerManager;

    public ArrayValueHandler(ValueHandlerManager valueHandlerManager) {
        this.valueHandlerManager = valueHandlerManager;
    }

    @Override
    public void handleValue(JsonArrayBuilder builder, FieldValue fieldValue) {
        builder.add(ArrayToJsonArray.convert(valueHandlerManager, fieldValue.getValue()));
    }
}