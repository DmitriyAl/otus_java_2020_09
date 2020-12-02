package otus.java.serialization.handler.value;

import otus.java.serialization.model.FieldValue;
import otus.java.serialization.processor.FieldProcessor;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class ObjectValueHandler implements ValueHandler {
    private final FieldProcessor processor;

    public ObjectValueHandler(FieldProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void handleValue(JsonArrayBuilder builder, FieldValue fieldValue) {
        JsonObjectBuilder innerObjectBuilder = Json.createObjectBuilder();
        processor.processFields(innerObjectBuilder, fieldValue.getValue());
        builder.add(innerObjectBuilder);
    }
}