package otus.java.serialization.handler.field;

import otus.java.serialization.model.ParsedField;

import javax.json.JsonObjectBuilder;
import java.math.BigDecimal;

public class BigDecimalFieldHandler implements FieldHandler {
    @Override
    public void handleField(JsonObjectBuilder builder, ParsedField parsedField) {
        builder.add(parsedField.getFieldName(), (BigDecimal) parsedField.getValue());
    }
}