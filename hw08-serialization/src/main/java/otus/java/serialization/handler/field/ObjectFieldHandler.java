package otus.java.serialization.handler.field;

import otus.java.serialization.model.ParsedField;
import otus.java.serialization.processor.FieldProcessor;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class ObjectFieldHandler implements FieldHandler {
    private final FieldProcessor processor;

    public ObjectFieldHandler(FieldProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void handleField(JsonObjectBuilder builder, ParsedField parsedField) {
        JsonObjectBuilder innerObjectBuilder = Json.createObjectBuilder();
        processor.processFields(innerObjectBuilder, parsedField.getValue());
        builder.add(parsedField.getFieldName(), innerObjectBuilder);
    }
}
