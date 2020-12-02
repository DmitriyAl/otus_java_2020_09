package otus.java.serialization.handler.field;

import otus.java.serialization.handler.value.ValueHandlerManager;
import otus.java.serialization.model.ParsedField;
import otus.java.serialization.util.CollectionToJsonArray;

import javax.json.JsonObjectBuilder;

public class CollectionFieldHandler implements FieldHandler {
    private final ValueHandlerManager valueHandlerManager;

    public CollectionFieldHandler(ValueHandlerManager valueHandlerManager) {
        this.valueHandlerManager = valueHandlerManager;
    }

    @Override
    public void handleField(JsonObjectBuilder builder, ParsedField parsedField) {
        builder.add(parsedField.getFieldName(), CollectionToJsonArray.convert(valueHandlerManager, parsedField.getValue()));
    }
}