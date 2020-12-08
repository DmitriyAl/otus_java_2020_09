package otus.java.serialization;

import otus.java.serialization.model.FieldType;
import otus.java.serialization.processor.ObjectProcessor;
import otus.java.serialization.util.FieldTypeDefiner;

import javax.json.*;

public class MyGson {
    private final ObjectProcessor objectProcessor;

    public MyGson() {
        this.objectProcessor = new ObjectProcessor();
    }

    public String toJson(Object object) {
        if (object == null) {
            return "null";
        }
        FieldType fieldType = FieldTypeDefiner.defineType(object);
        if (fieldType == FieldType.OBJECT) {
            return handleObject(object).toString();
        } else if (fieldType == FieldType.COLLECTION) {
            return handleCollection(object).toString();
        } else if (fieldType == FieldType.ARRAY) {
            return handleArray(object).toString();
        } else if (fieldType == FieldType.CHARACTER || fieldType == FieldType.STRING) {
            return handleCharSequence(object);
        } else {
            return object.toString();
        }
    }

    private String handleCharSequence(Object object) {
        return String.format("\"%s\"", object.toString());
    }

    private JsonArray handleCollection(Object object) {
        return objectProcessor.processCollection(object).build();
    }

    private JsonArray handleArray(Object object) {
        return objectProcessor.processArray(object).build();
    }

    private JsonObject handleObject(Object object) {
        return objectProcessor.processFields(object).build();
    }
}