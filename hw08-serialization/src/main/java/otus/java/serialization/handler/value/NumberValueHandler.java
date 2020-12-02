package otus.java.serialization.handler.value;

import otus.java.serialization.model.FieldValue;
import otus.java.serialization.util.FieldTypeDefiner;

import javax.json.JsonArrayBuilder;

public class NumberValueHandler implements ValueHandler {
    @Override
    public void handleValue(JsonArrayBuilder builder, FieldValue fieldValue) {
        Number value = (Number) fieldValue.getValue();
        if (FieldTypeDefiner.isFractional(value)) {
            builder.add(value.doubleValue());
        } else {
            builder.add(value.longValue());
        }
    }
}