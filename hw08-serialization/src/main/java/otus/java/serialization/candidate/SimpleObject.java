package otus.java.serialization.candidate;

import java.math.BigDecimal;
import java.util.List;

public class SimpleObject {
    private String stringValue;
    private int intValue;
    private float[] floatArray;
    private Object[] objectsArray;
    private boolean booleanValue;
    private List<Long> longList;
    private List<?> objectsList;
    private BigDecimal bigDecimalValue;
    private String nullValue;

    public SimpleObject() {
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public float[] getFloatArray() {
        return floatArray;
    }

    public void setFloatArray(float[] floatArray) {
        this.floatArray = floatArray;
    }

    public Object[] getObjectsArray() {
        return objectsArray;
    }

    public void setObjectsArray(Object[] objectsArray) {
        this.objectsArray = objectsArray;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public List<Long> getLongList() {
        return longList;
    }

    public void setLongList(List<Long> longList) {
        this.longList = longList;
    }

    public List<?> getObjectsList() {
        return objectsList;
    }

    public void setObjectsList(List<?> objectsList) {
        this.objectsList = objectsList;
    }

    public BigDecimal getBigDecimalValue() {
        return bigDecimalValue;
    }

    public void setBigDecimalValue(BigDecimal bigDecimalValue) {
        this.bigDecimalValue = bigDecimalValue;
    }
}