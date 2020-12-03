package otus.java.serialization.candidate;

import java.util.List;
import java.util.Objects;

public class ComplexObject {
    private String stringValue;
    private SimpleObject object;
    private List<?> objects;

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public SimpleObject getObject() {
        return object;
    }

    public void setObject(SimpleObject object) {
        this.object = object;
    }

    public List<?> getObjects() {
        return objects;
    }

    public void setObjects(List<?> objects) {
        this.objects = objects;
    }

    @Override
    public boolean equals(Object object1) {
        if (this == object1) return true;
        if (object1 == null || getClass() != object1.getClass()) return false;

        ComplexObject that = (ComplexObject) object1;

        if (!Objects.equals(stringValue, that.stringValue)) return false;
        if (!Objects.equals(object, that.object)) return false;
        return Objects.equals(objects, that.objects);
    }

    @Override
    public int hashCode() {
        int result = stringValue != null ? stringValue.hashCode() : 0;
        result = 31 * result + (object != null ? object.hashCode() : 0);
        result = 31 * result + (objects != null ? objects.hashCode() : 0);
        return result;
    }
}