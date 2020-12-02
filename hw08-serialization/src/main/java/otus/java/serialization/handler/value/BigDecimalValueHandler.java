package otus.java.serialization.handler.value;

import otus.java.serialization.model.FieldValue;

import javax.json.JsonArrayBuilder;
import java.math.BigDecimal;

public class BigDecimalValueHandler implements ValueHandler {
    @Override
    public void handleValue(JsonArrayBuilder builder, FieldValue fieldValue) {
        builder.add((BigDecimal) fieldValue.getValue());
    }
}