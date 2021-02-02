package otus.java.di.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ParsedConfiguration {
    private final Class<?> clazz;
    private final List<Method> beans;

    public ParsedConfiguration(Class<?> clazz, Method bean) {
        this.clazz = clazz;
        this.beans = new ArrayList<>();
        this.beans.add(bean);
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public List<Method> getBeans() {
        return beans;
    }
}