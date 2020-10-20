package hw02.generics;

public class TestClass<T extends Comparable<T>> {
    private final T value;

    public TestClass(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "value='" + value + '\'' +
                '}';
    }
}
