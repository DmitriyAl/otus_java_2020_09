package otus.java.annotations.test;

import otus.java.annotations.annotation.After;
import otus.java.annotations.annotation.Before;
import otus.java.annotations.annotation.Test;

public class TestClassWithFailedBefore {
    @Before
    public void failedBefore() {
        throw new RuntimeException(String.format("Failed before of failed before class. Hashcode=%d", this.hashCode()));
    }

    @Test
    public void testClassWithFailedBeforeTest() {
        System.out.print("We won't be here");
    }

    @After
    public void afterWithException() {
        System.out.printf("Successful after of failed before class. Hashcode=%d\n", this.hashCode());
    }
}