package otus.java.serialization.handler.value;

import otus.java.serialization.model.FieldValue;

import javax.json.JsonArrayBuilder;

public interface ValueHandler {
    void handleValue(JsonArrayBuilder builder, FieldValue fieldValue);
}