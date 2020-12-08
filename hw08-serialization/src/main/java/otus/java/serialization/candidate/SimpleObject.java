package otus.java.serialization.candidate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        SimpleObject that = (SimpleObject) object;

        if (intValue != that.intValue) return false;
        if (booleanValue != that.booleanValue) return false;
        if (!Objects.equals(stringValue, that.stringValue)) return false;
        if (!Arrays.equals(floatArray, that.floatArray)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(objectsArray, that.objectsArray)) return false;
        if (!Objects.equals(longList, that.longList)) return false;
        if (!Objects.equals(objectsList, that.objectsList)) return false;
        if (!Objects.equals(bigDecimalValue, that.bigDecimalValue))
            return false;
        return Objects.equals(nullValue, that.nullValue);
    }

    @Override
    public int hashCode() {
        int result = stringValue != null ? stringValue.hashCode() : 0;
        result = 31 * result + intValue;
        result = 31 * result + Arrays.hashCode(floatArray);
        result = 31 * result + Arrays.hashCode(objectsArray);
        result = 31 * result + (booleanValue ? 1 : 0);
        result = 31 * result + (longList != null ? longList.hashCode() : 0);
        result = 31 * result + (objectsList != null ? objectsList.hashCode() : 0);
        result = 31 * result + (bigDecimalValue != null ? bigDecimalValue.hashCode() : 0);
        result = 31 * result + (nullValue != null ? nullValue.hashCode() : 0);
        return result;
    }
}