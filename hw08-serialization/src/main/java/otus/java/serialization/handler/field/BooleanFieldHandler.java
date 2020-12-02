package otus.java.serialization.handler.field;

import otus.java.serialization.model.ParsedField;

import javax.json.JsonObjectBuilder;

public class BooleanFieldHandler implements FieldHandler {

    @Override
    public void handleField(JsonObjectBuilder builder, ParsedField parsedField) {
        builder.add(parsedField.getFieldName(), (Boolean) parsedField.getValue());
    }
}