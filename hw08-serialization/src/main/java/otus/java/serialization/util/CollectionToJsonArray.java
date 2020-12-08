package otus.java.serialization.util;

import otus.java.serialization.handler.value.ValueHandlerManager;
import otus.java.serialization.model.FieldType;
import otus.java.serialization.model.FieldValue;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import java.util.Collection;

public class CollectionToJsonArray {
    public static JsonArrayBuilder convert(ValueHandlerManager valueHandlerManager, Object object) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        Collection<?> collection = (Collection<?>) object;
        for (Object element : collection) {
            FieldType type = FieldTypeDefiner.defineType(element);
            valueHandlerManager.getHandler(type).handleValue(arrayBuilder, new FieldValue(element, type));
        }
        return arrayBuilder;
    }
}