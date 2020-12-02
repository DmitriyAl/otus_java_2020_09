package otus.java.serialization.handler.value;

import otus.java.serialization.model.FieldValue;

import javax.json.JsonArrayBuilder;

public class BooleanValueHandler implements ValueHandler {

    @Override
    public void handleValue(JsonArrayBuilder builder, FieldValue fieldValue) {
        builder.add((Boolean) fieldValue.getValue());
    }
}