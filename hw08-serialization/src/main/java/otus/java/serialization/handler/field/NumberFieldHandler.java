package otus.java.serialization.handler.field;

import otus.java.serialization.model.ParsedField;
import otus.java.serialization.util.FieldTypeDefiner;

import javax.json.JsonObjectBuilder;

public class NumberFieldHandler implements FieldHandler {

    @Override
    public void handleField(JsonObjectBuilder builder, ParsedField parsedField) {
        Number value = (Number) parsedField.getValue();
        if (FieldTypeDefiner.isFractional(value)) {
            builder.add(parsedField.getFieldName(), value.doubleValue());
        } else {
            builder.add(parsedField.getFieldName(), value.longValue());
        }
    }
}