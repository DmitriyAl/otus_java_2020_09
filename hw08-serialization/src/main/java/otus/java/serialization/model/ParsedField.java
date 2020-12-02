package otus.java.serialization.model;

public class ParsedField {
    private final String fieldName;
    private final FieldValue fieldValue;

    public ParsedField(String fieldName, FieldValue fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getValue() {
        return fieldValue.getValue();
    }

    public FieldType getType() {
        return fieldValue.getType();
    }
}