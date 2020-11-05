package otus.java.annotations.test;

import otus.java.annotations.annotation.After;
import otus.java.annotations.annotation.Before;
import otus.java.annotations.annotation.Test;

public class TestClassWithFailedAfter {
    @Before
    public void before() {
        System.out.printf("Successful before of failed after class. Hashcode=%d\n", this.hashCode());
    }

    @Test
    public void testClassWithFailedAfterTest() {
        System.out.printf("Successful test method of failed after class. Hashcode=%d\n", this.hashCode());
    }

    @After
    public void afterWithException() {
        throw new RuntimeException(String.format("Failed after of failed after class. Hashcode=%d", this.hashCode()));
    }
}