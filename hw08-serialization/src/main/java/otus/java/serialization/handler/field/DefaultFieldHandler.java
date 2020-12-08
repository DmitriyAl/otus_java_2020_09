package otus.java.serialization.handler.field;

import otus.java.serialization.model.ParsedField;

import javax.json.JsonObjectBuilder;

public class DefaultFieldHandler implements FieldHandler {

    @Override
    public void handleField(JsonObjectBuilder builder, ParsedField parsedField) {
        Object value = parsedField.getValue();
        builder.add(parsedField.getFieldName(), value == null ? null : value.toString());
    }
}