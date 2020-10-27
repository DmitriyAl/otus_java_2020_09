package otus.java.annotations.exception;

import java.lang.reflect.Method;

public class TestMethodFailedException extends Exception {
    public TestMethodFailedException(Method method, Throwable targetException) {
        super(String.format("The test method '%s' failed with exception: %s"
                , method.getName(), targetException.getMessage()), targetException);
    }
}