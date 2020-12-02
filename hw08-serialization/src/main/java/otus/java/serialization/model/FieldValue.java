package otus.java.serialization.model;

public class FieldValue {
    private final Object value;
    private final FieldType type;

    public FieldValue(Object value, FieldType type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public FieldType getType() {
        return type;
    }
}