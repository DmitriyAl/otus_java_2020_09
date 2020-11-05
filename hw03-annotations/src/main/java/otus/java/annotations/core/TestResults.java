package otus.java.annotations.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestResults {
    private final List<Method> successful;
    private final List<Method> failed;

    public TestResults() {
        successful = new ArrayList<>();
        failed = new ArrayList<>();
    }

    public TestResults(List<Method> successful, List<Method> failed) {
        this.successful = successful;
        this.failed = failed;
    }

    public List<Method> getSuccessful() {
        return successful;
    }

    public List<Method> getFailed() {
        return failed;
    }
}