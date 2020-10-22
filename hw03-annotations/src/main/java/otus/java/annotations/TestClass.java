package otus.java.annotations;

public class TestClass {
    @Before
    public void oneBefore() {
        System.out.println(String.format("One before. It happens before the test. Class hashCode = %d", this.hashCode()));
    }

    @Before
    public void anotherBefore() {
        System.out.println(String.format("Another before. It happens before the test. Class hashCode = %d", this.hashCode()));
    }

    @Test
    public void firstTest() {
        System.out.println(String.format("The first test which passes. Class hashCode = %d", this.hashCode()));
    }

    @Test
    public void secondTest() throws Exception {
        System.out.println(String.format("The second test which throws an Exception. " +
                "Class hashCode = %d", this.hashCode()));
        throw new Exception("Exception from second test");
    }

    @Test
    public void thirdTest() {
        System.out.println(String.format("The third test which passes. Class hashCode = %d", this.hashCode()));
    }

    @After
    public void after() {
        System.out.println(String.format("It happens after the test. Class hashCode = %d", this.hashCode()));
    }
}
