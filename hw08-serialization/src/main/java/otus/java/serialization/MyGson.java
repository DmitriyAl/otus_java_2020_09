package otus.java.serialization;

import otus.java.serialization.processor.FieldProcessor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class MyGson {
    private final FieldProcessor fieldProcessor;

    public MyGson(FieldProcessor fieldProcessor) {
        this.fieldProcessor = fieldProcessor;
    }

    public String toJson(Object object) {
        JsonObject jsonObject = handleFields(object);
        return jsonObject.toString();
    }

    private JsonObject handleFields(Object object) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        fieldProcessor.processFields(builder, object);
        return builder.build();
    }
}