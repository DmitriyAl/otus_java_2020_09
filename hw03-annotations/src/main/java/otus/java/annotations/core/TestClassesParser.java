package otus.java.annotations.core;

import otus.java.annotations.annotation.After;
import otus.java.annotations.annotation.Before;
import otus.java.annotations.annotation.Test;
import otus.java.annotations.comparator.BeforeComparator;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestClassesParser {
    public List<ParsedTestClass> parseTestClasses(Class<?>... classes) {
        return Stream.of(classes).map(this::parseTestClass).collect(Collectors.toList());
    }

    public ParsedTestClass parseTestClass(Class<?> testClass) {
        Method[] methods = testClass.getMethods();
        List<Method> testMethods
                = Stream.of(methods).filter(m -> m.isAnnotationPresent(Test.class)).collect(Collectors.toList());
        List<Method> beforeMethods
                = Stream.of(methods).filter(m -> m.isAnnotationPresent(Before.class))
                .sorted(new BeforeComparator()).collect(Collectors.toList());
        List<Method> afterMethods
                = Stream.of(methods).filter(m -> m.isAnnotationPresent(After.class)).collect(Collectors.toList());
        return new ParsedTestClass(testClass, beforeMethods, testMethods, afterMethods);
    }
}