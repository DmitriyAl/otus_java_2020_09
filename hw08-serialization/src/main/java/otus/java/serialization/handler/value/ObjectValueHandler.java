package otus.java.serialization.handler.value;

import otus.java.serialization.model.FieldValue;
import otus.java.serialization.processor.ObjectProcessor;

import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class ObjectValueHandler implements ValueHandler {
    private final ObjectProcessor processor;

    public ObjectValueHandler(ObjectProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void handleValue(JsonArrayBuilder builder, FieldValue fieldValue) {
        JsonObjectBuilder innerObjectBuilder = processor.processFields(fieldValue.getValue());
        builder.add(innerObjectBuilder);
    }
}