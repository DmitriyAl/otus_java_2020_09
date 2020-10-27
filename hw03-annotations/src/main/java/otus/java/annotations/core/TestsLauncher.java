package otus.java.annotations.core;

import otus.java.annotations.exception.TestMethodFailedException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class TestsLauncher {
    public void launchTests(Class<?>... classes) {
        List<ParsedTestClass> testClasses = new TestClassesParser().parseTestClasses(classes);
        TestResults results = executeTestClasses(testClasses);
        publishResults(results);
    }

    private TestResults executeTestClasses(List<ParsedTestClass> testClasses) {
        return testClasses.stream().map(this::executeTestClass).reduce(this::combineResults).orElse(null);
    }

    private TestResults executeTestClass(ParsedTestClass testClass) {
        testsSplitter(testClass);
        TestResults results = new TestResults();
        for (Method testMethod : testClass.getTests()) {
            Object instance = null;
            boolean testSuccessful = true;
            try {
                instance = testClass.getTestClass().getConstructor().newInstance();
                processBeforeMethods(instance, testClass.getBefore());
                processTest(instance, testMethod);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                testSuccessful = false;
            } finally {
                boolean afterSuccessful = processAfterMethods(instance, testClass.getAfter());
                handleResult(results, testMethod, testSuccessful, afterSuccessful);
                System.out.println();
            }
        }
        return results;
    }

    private void testsSplitter(ParsedTestClass testClass) {
        System.out.println();
        System.out.println("####################################################################");
        System.out.printf("'%s' tests\n", testClass.getTestClass().getSimpleName());
        System.out.println("--------------------------------------------------------------------");
    }

    private void processBeforeMethods(Object instance, List<Method> before) throws TestMethodFailedException {
        for (Method method : before) {
            processMethod(instance, method);
        }
    }

    private void processTest(Object instance, Method testMethod) throws TestMethodFailedException {
        processMethod(instance, testMethod);
    }

    private void processMethod(Object instance, Method method) throws TestMethodFailedException {
        try {
            method.invoke(instance);
        } catch (IllegalAccessException e) {
            throw new TestMethodFailedException(method, e);
        } catch (InvocationTargetException e) {
            throw new TestMethodFailedException(method, e.getTargetException());
        } finally {
        }
    }

    private boolean processAfterMethods(Object instance, List<Method> after) {
        boolean successful = false;
        if (instance != null) {
            for (Method method : after) {
                try {
                    method.invoke(instance);
                    successful = true;
                } catch (IllegalAccessException e) {
                    System.out.printf("'%s' after method was failed\n", method.getName());
                } catch (InvocationTargetException e) {
                    System.out.println(e.getTargetException().getMessage());
                }
            }
        }
        return successful;
    }

    private void handleResult(TestResults results, Method testMethod, boolean testSuccessful, boolean afterSuccessful) {
        if (testSuccessful && afterSuccessful) {
            results.getSuccessful().add(testMethod);
        } else {
            results.getFailed().add(testMethod);
        }
    }

    private TestResults combineResults(TestResults r1, TestResults r2) {
        r1.getSuccessful().addAll(r2.getSuccessful());
        r1.getFailed().addAll(r2.getFailed());
        return r1;
    }

    private void publishResults(TestResults results) {
        System.out.println("Successful tests: " + results.getSuccessful().stream().map(Method::getName).collect(Collectors.toList()));
        System.out.println("Failed tests: " + results.getFailed().stream().map(Method::getName).collect(Collectors.toList()));
    }
}