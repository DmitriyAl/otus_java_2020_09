package otus.java.serialization.handler.field;

import otus.java.serialization.model.ParsedField;

import javax.json.JsonObjectBuilder;

public interface FieldHandler {
    void handleField(JsonObjectBuilder builder, ParsedField parsedField);
}