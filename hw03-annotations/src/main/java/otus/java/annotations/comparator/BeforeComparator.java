package otus.java.annotations.comparator;

import otus.java.annotations.annotation.Before;

import java.lang.reflect.Method;
import java.util.Comparator;

public class BeforeComparator implements Comparator<Method> {

    @Override
    public int compare(Method m1, Method m2) {
        Before before1 = m1.getAnnotation(Before.class);
        Before before2 = m2.getAnnotation(Before.class);
        return before2.priority() - before1.priority();
    }
}