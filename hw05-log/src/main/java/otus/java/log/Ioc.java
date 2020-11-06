package otus.java.log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {
    private Ioc() {
    }

    public static Loggable getLoggableClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (Loggable) Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{Loggable.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final Loggable instance;

        public DemoInvocationHandler(Loggable instance) {
            this.instance = instance;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
            method = instance.getClass().getMethod(method.getName(), method.getParameterTypes());
            if (method.isAnnotationPresent(Log.class)) {
                System.out.printf("Executed method: %s; params: %s\n", method.getName(), Arrays.toString(args));
            }
            return method.invoke(instance, args);
        }
    }
}