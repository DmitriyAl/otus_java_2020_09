package otus.java.serialization;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import otus.java.serialization.candidate.ComplexObject;
import otus.java.serialization.candidate.SimpleObject;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        MyGson myGson = new MyGson();
        String json = gson.toJson(simpleObject);
        String myJson = myGson.toJson(simpleObject);
        assertThat(myJson).isEqualTo(json);
    }

    @Test
    public void objectWithInnerObject() {
        ComplexObject complexObject = new ComplexObject();
        complexObject.setStringValue("test");
        complexObject.setObject(simpleObject);
        complexObject.setObjects(Arrays.asList(1, 2.f, true, simpleObject, new Object[]{"array"}, Collections.singletonList(1)));
        Gson gson = new Gson();
        MyGson myGson = new MyGson();
        String json = gson.toJson(complexObject);
        String myJson = myGson.toJson(complexObject);
        assertThat(myJson).isEqualTo(json);
    }

    @Test
    void test() {
        Gson gson = new Gson();
        MyGson serializer = new MyGson();
        assertEquals(gson.toJson(null), serializer.toJson(null));
        assertEquals(gson.toJson((byte)1), serializer.toJson((byte)1));
        assertEquals(gson.toJson((short)1f), serializer.toJson((short)1f));
        assertEquals(gson.toJson(1), serializer.toJson(1));
        assertEquals(gson.toJson(1L), serializer.toJson(1L));
        assertEquals(gson.toJson(1f), serializer.toJson(1f));
        assertEquals(gson.toJson(1d), serializer.toJson(1d));
        assertEquals(gson.toJson("aaa"), serializer.toJson("aaa"));
        assertEquals(gson.toJson('a'), serializer.toJson('a'));
        assertEquals(gson.toJson(new int[] {1, 2, 3}), serializer.toJson(new int[] {1, 2, 3}));
        assertEquals(gson.toJson(List.of(1, 2 ,3)), serializer.toJson(List.of(1, 2 ,3)));
        assertEquals(gson.toJson(Collections.singletonList(1)), serializer.toJson(Collections.singletonList(1)));
    }
}