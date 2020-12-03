package otus.java.serialization;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.java.serialization.candidate.SimpleObject;
import otus.java.serialization.candidate.ComplexObject;
import otus.java.serialization.processor.FieldProcessor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class MyGsonTest {
    private SimpleObject simpleObject;

    @BeforeEach
    void init() {
        simpleObject = new SimpleObject();
        simpleObject.setStringValue("Test");
        simpleObject.setIntValue(1);
        simpleObject.setFloatArray(new float[]{1f, 2.5f});
        simpleObject.setObjectsArray(new Object[]{"array", 1});
        simpleObject.setLongList(Arrays.asList(1L, 2L));
        simpleObject.setObjectsList(Arrays.asList(1, "Home", 4.5f));
        simpleObject.setBooleanValue(true);
        simpleObject.setBigDecimalValue(new BigDecimal("99999999999999999999999999.999999999999999999999999999999999"));
    }

    @Test
    public void setSimpleObject() {
        Gson gson = new Gson();
        MyGson myGson = new MyGson(new FieldProcessor());
        String json = gson.toJson(simpleObject);
        String myJson = myGson.toJson(simpleObject);
        assertThat(myJson).isEqualTo(json);
        SimpleObject fromJson = gson.fromJson(json, SimpleObject.class);
//        assertThat(fromJson).isEqualToComparingFieldByField(simpleObject);
    }

    @Test
    public void objectWithInnerObject() {
        ComplexObject complexObject = new ComplexObject();
        complexObject.setStringValue("test");
        complexObject.setObject(simpleObject);
        complexObject.setObjects(Arrays.asList(1, 2.f, true, simpleObject, new Object[]{"array"}, Collections.singletonList(1)));
        Gson gson = new Gson();
        MyGson myGson = new MyGson(new FieldProcessor());
        String json = gson.toJson(complexObject);
        String myJson = myGson.toJson(complexObject);
        assertThat(myJson).isEqualTo(json);
        ComplexObject fromJson = gson.fromJson(json, ComplexObject.class);
//        assertThat(fromJson).isEqualToComparingFieldByField(complexObject);
    }
}