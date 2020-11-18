package otus.java.log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Ioc {
    private Ioc() {
    }

    public static Loggable getLoggableClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (Loggable) Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class<?>[]{Loggable.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final Loggable instance;
        private final List<String> loggableMethods;

        public DemoInvocationHandler(Loggable instance) {
            this.instance = instance;
            this.loggableMethods = getLoggableMethods();
        }

        private List<String> getLoggableMethods() {
            return Arrays.stream(instance.getClass().getDeclaredMethods())
                    .filter(m -> m.isAnnotationPresent(Log.class))
                    .map(this::toStringSignature)
                    .collect(Collectors.toList());
        }

        private String toStringSignature(Method method) {
            String signature = method.toString();
            return signature.substring(signature.indexOf(method.getName()));
        }

        private boolean isLoggable(Method method) {
            return loggableMethods.contains(toStringSignature(method));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
            if (isLoggable(method)) {
                System.out.printf("Executed method: %s; params: %s\n", method.getName(), Arrays.toString(args));
            }
            return method.invoke(instance, args);
        }
    }
}