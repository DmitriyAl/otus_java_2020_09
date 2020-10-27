package otus.java.annotations.test;

import otus.java.annotations.annotation.After;
import otus.java.annotations.annotation.Before;
import otus.java.annotations.annotation.Test;

public class TestClass {
    @Before(priority = 1)
    public void firstBefore() {
        System.out.println(String.format("The first before. It happens before the test. Class hashCode = %d", this.hashCode()));
    }

    @Before
    public void secondBefore() {
        System.out.println(String.format("The second before. It happens before the test. Class hashCode = %d", this.hashCode()));
    }

    @Test
    public void testClassFirstTest() {
        System.out.println(String.format("The first test which passes. Class hashCode = %d", this.hashCode()));
    }

    @Test
    public void testClassSecondTest() throws Exception {
        throw new Exception(String.format("Exception from testClassSecondTest test. Class hashcode = %d", this.hashCode()));
    }

    @Test
    public void testClassThirdTest() {
        System.out.println(String.format("The third test which passes. Class hashCode = %d", this.hashCode()));
    }

    @After
    public void after() {
        System.out.println(String.format("It happens after the test. Class hashCode = %d", this.hashCode()));
    }
}
