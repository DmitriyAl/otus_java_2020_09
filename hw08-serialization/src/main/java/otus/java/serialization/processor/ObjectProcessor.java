package otus.java.serialization.processor;

import otus.java.serialization.handler.field.FieldHandler;
import otus.java.serialization.handler.field.FieldHandlerManager;
import otus.java.serialization.handler.value.ValueHandlerManager;
import otus.java.serialization.model.FieldType;
import otus.java.serialization.model.FieldValue;
import otus.java.serialization.model.ParsedField;
import otus.java.serialization.util.ArrayToJsonArray;
import otus.java.serialization.util.CollectionToJsonArray;
import otus.java.serialization.util.FieldTypeDefiner;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;

public class ObjectProcessor {
    private final ValueHandlerManager valueHandlerManager;
    private final FieldHandlerManager fieldHandlerManager;

    public ObjectProcessor() {
        this.valueHandlerManager = new ValueHandlerManager(this);
        this.fieldHandlerManager = new FieldHandlerManager(this, valueHandlerManager);
    }

    public JsonObjectBuilder processFields(Object object) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            processField(builder, field, object);
        }
        return builder;
    }

    public JsonArrayBuilder processCollection(Object object) {
        return CollectionToJsonArray.convert(valueHandlerManager, object);
    }

    public JsonArrayBuilder processArray(Object object) {
        return ArrayToJsonArray.convert(valueHandlerManager, object);
    }

    private void processField(JsonObjectBuilder builder, Field field, Object object) {
        ParsedField parsedField = parseField(field, object);
        if (parsedField == null) {
            return;
        }
        FieldHandler handler = fieldHandlerManager.getHandler(parsedField.getType());
        handler.handleField(builder, parsedField);
    }

    private ParsedField parseField(Field field, Object object) {
        Object value = getFieldValue(field, object);
        if (value == null) {
            return null;
        }
        FieldType type = FieldTypeDefiner.defineType(value);
        return new ParsedField(field.getName(), new FieldValue(value, type));
    }

    private Object getFieldValue(Field field, Object object) {
        field.setAccessible(true);
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }
}