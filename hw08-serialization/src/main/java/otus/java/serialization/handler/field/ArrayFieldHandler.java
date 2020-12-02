package otus.java.serialization.handler.field;

import otus.java.serialization.util.ArrayToJsonArray;
import otus.java.serialization.handler.value.ValueHandlerManager;
import otus.java.serialization.model.ParsedField;

import javax.json.JsonObjectBuilder;

public class ArrayFieldHandler implements FieldHandler {
    private final ValueHandlerManager valueHandlerManager;

    public ArrayFieldHandler(ValueHandlerManager valueHandlerManager) {
        this.valueHandlerManager = valueHandlerManager;
    }

    @Override
    public void handleField(JsonObjectBuilder builder, ParsedField parsedField) {
        builder.add(parsedField.getFieldName(), ArrayToJsonArray.convert(valueHandlerManager, parsedField.getValue()));
    }
}