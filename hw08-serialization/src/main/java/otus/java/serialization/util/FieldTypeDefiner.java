package otus.java.serialization.util;

import otus.java.serialization.model.FieldType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

public class FieldTypeDefiner {
    public static FieldType defineType(Object object) {
        Class<?> clazz = object.getClass();
        if (BigDecimal.class.isAssignableFrom(clazz)) {
            return FieldType.BIG_DECIMAL;
        }
        if (BigInteger.class.isAssignableFrom(clazz)) {
            return FieldType.BIG_INTEGER;
        }
        if (Number.class.isAssignableFrom(clazz)) {
            return FieldType.NUMBER_WRAPPER;
        }
        if (Boolean.class.isAssignableFrom(clazz)) {
            return FieldType.BOOLEAN;
        }
        if (Collection.class.isAssignableFrom(clazz)) {
            return FieldType.COLLECTION;
        }
        if (clazz.isArray()) {
            return FieldType.ARRAY;
        }
        if (String.class.isAssignableFrom(clazz)) {
            return FieldType.STRING;
        }
        return FieldType.OBJECT;
    }

    public static boolean isFractional(Number number) {
        return number instanceof Float || number instanceof Double || number instanceof BigDecimal;
    }
}