package otus.java.annotations.core;

import java.lang.reflect.Method;
import java.util.List;

public class ParsedTestClass {
    private final Class<?> testClass;
    private final List<Method> before;
    private final List<Method> tests;
    private final List<Method> after;

    public ParsedTestClass(Class<?> testClass, List<Method> before, List<Method> tests, List<Method> after) {
        this.testClass = testClass;
        this.before = before;
        this.tests = tests;
        this.after = after;
    }

    public Class<?> getTestClass() {
        return testClass;
    }

    public List<Method> getBefore() {
        return before;
    }

    public List<Method> getTests() {
        return tests;
    }

    public List<Method> getAfter() {
        return after;
    }
}