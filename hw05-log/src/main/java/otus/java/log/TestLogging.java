package otus.java.log;

public class TestLogging implements Loggable {
    @Override
    public void calculation(int param) {
        System.out.println("Not loggable method with 1 param");
    }

    @Override
    @Log
    public void calculation(int param1, int param2) {
        System.out.println("Loggable method with 2 params");
    }

    @Override
    @Log
    public void calculation(int param1, int param2, String param3) {
        System.out.println("Loggable method with 3 params");
    }
}