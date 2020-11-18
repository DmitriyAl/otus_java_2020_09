package otus.java.log;

public class Demo {
    public static void main(String[] args) {
        Loggable loggableClass = Ioc.getLoggableClass();
        loggableClass.calculation(1);
        loggableClass.calculation(1, 0);
        loggableClass.calculation(1, 0, "test");
    }
}