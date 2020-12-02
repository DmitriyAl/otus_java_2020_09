package otus.java.serialization.handler.field;

import otus.java.serialization.model.ParsedField;

import javax.json.JsonObjectBuilder;
import java.math.BigInteger;

public class BigIntegerFieldHandler implements FieldHandler {
    @Override
    public void handleField(JsonObjectBuilder builder, ParsedField parsedField) {
        builder.add(parsedField.getFieldName(), (BigInteger) parsedField.getValue());
    }
}