package otus.java.serialization.handler.field;

import otus.java.serialization.model.ParsedField;
import otus.java.serialization.processor.ObjectProcessor;

import javax.json.JsonObjectBuilder;

public class ObjectFieldHandler implements FieldHandler {
    private final ObjectProcessor processor;

    public ObjectFieldHandler(ObjectProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void handleField(JsonObjectBuilder builder, ParsedField parsedField) {
        JsonObjectBuilder innerObjectBuilder = processor.processFields(parsedField.getValue());
        builder.add(parsedField.getFieldName(), innerObjectBuilder);
    }
}
