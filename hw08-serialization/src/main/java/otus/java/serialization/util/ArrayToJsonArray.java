package otus.java.serialization.util;

import otus.java.serialization.handler.value.ValueHandlerManager;
import otus.java.serialization.model.FieldType;
import otus.java.serialization.model.FieldValue;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.lang.reflect.Array;

public class ArrayToJsonArray {
    public static JsonArrayBuilder convert(ValueHandlerManager valueHandlerManager, Object array) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            Object element = Array.get(array, i);
            FieldType type = FieldTypeDefiner.defineType(element);
            valueHandlerManager.getHandler(type).handleValue(arrayBuilder, new FieldValue(element, type));
        }
        return arrayBuilder;
    }
}