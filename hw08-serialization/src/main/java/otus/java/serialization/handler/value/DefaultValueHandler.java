package otus.java.serialization.handler.value;

import otus.java.serialization.model.FieldValue;

import javax.json.JsonArrayBuilder;

public class DefaultValueHandler implements ValueHandler {
    @Override
    public void handleValue(JsonArrayBuilder builder, FieldValue fieldValue) {
        Object value = fieldValue.getValue();
        builder.add(value == null ? null : value.toString());
    }
}