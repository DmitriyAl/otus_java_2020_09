package otus.java.jdbc.mapper;

import otus.java.jdbc.anotation.Id;
import otus.java.jdbc.exception.DefaultConstructorRequiredException;
import otus.java.jdbc.exception.IdFieldRequiredException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;
    private String name;
    private Constructor<T> constructor;
    private Field idField;
    private List<Field> allFields;
    private List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        if (name == null) {
            name = clazz.getSimpleName();
        }
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        if (constructor == null) {
            try {
                constructor = clazz.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new DefaultConstructorRequiredException("Default constructor should be implemented");
            }
        }
        return constructor;
    }

    @Override
    public Field getIdField() {
        if (idField == null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            idField = Arrays.stream(declaredFields).filter(f -> f.isAnnotationPresent(Id.class)).limit(1).findFirst()
                    .orElseThrow(() -> new IdFieldRequiredException("Id field is required"));
        }
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        if (allFields == null) {
            List<Field> fields = new ArrayList<>();
            fields.add(getIdField());
            fields.addAll(getFieldsWithoutId());
            allFields = Collections.unmodifiableList(fields);
        }
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        if (fieldsWithoutId == null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            fieldsWithoutId = Arrays.stream(declaredFields).filter(f -> !f.isAnnotationPresent(Id.class))
                    .collect(Collectors.toList());
        }
        return fieldsWithoutId;
    }
}
