package hw02.generics;

import java.util.Comparator;

public class TestClassComparator<T extends Comparable<T>> implements Comparator<TestClass<T>> {
    @Override
    public int compare(TestClass<T> o1, TestClass<T> o2) {
        return o1.getValue().compareTo(o2.getValue());
    }
}
