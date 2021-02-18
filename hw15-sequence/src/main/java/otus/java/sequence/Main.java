package otus.java.sequence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static final int MIN = 0;
    public static final int MAX = 10;
    private int value = 0;
    private int step = 1;
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        final Main main = new Main();
        new Thread(() -> main.increase(1), "First").start();
        new Thread(() -> main.increase(0), "Second").start();
    }

    private synchronized void increase(int mod) {
        while (true) {
            try {
                while (value % 2 == mod) {
                    this.wait();
                }
                value = value + step;
                logger.info(String.valueOf(value));
                if (value == MAX || value == MIN) {
                    step = -step;
                }
                sleep();
                notifyAll();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}