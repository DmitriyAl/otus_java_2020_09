package otus.java.annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) throws Exception {
        Class<TestClass> testClass = TestClass.class;
        Method[] methods = testClass.getMethods();
        List<Method> testMethods
                = Stream.of(methods).filter(m -> m.isAnnotationPresent(Test.class)).collect(Collectors.toList());
        List<Method> beforeMethods
                = Stream.of(methods).filter(m -> m.isAnnotationPresent(Before.class)).collect(Collectors.toList());
        List<Method> afterMethods
                = Stream.of(methods).filter(m -> m.isAnnotationPresent(After.class)).collect(Collectors.toList());
        int successful = 0;
        for (Method testMethod : testMethods) {
            final TestClass instance = testClass.getConstructor().newInstance();
            beforeMethods.forEach(m -> invokeMethod(m, instance));
            var isSuccessful = invokeMethod(testMethod, instance);
            afterMethods.forEach(m -> invokeMethod(m, instance));
            System.out.println("#################################");
            if (isSuccessful) successful++;
        }
        System.out.println(String.format("%d test(s) launched", testMethods.size()));
        System.out.println(String.format("%d test(s) successful", successful));
        System.out.println(String.format("%d test(s) failed", testMethods.size() - successful));
    }

    private static boolean invokeMethod(Method method, TestClass instance) {
        try {
            method.invoke(instance);
            return true;
        } catch (IllegalAccessException e) {
            System.out.println(String.format("'%s' method couldn't be invoked", method.getName()));
        } catch (InvocationTargetException e) {
            Throwable exception = e.getTargetException();
            System.out.println(String.format("'%s' method threw an exception '%s'", method.getName(), exception.getMessage()));
        }
        return false;
    }
}
