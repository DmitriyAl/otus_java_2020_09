package otus.java.annotations;

import otus.java.annotations.core.TestsLauncher;
import otus.java.annotations.test.TestClass;
import otus.java.annotations.test.TestClassWithFailedAfter;
import otus.java.annotations.test.TestClassWithFailedBefore;

public class App {
    public static void main(String[] args) {
        new TestsLauncher().launchTests(TestClass.class, TestClassWithFailedAfter.class, TestClassWithFailedBefore.class);
    }
}
