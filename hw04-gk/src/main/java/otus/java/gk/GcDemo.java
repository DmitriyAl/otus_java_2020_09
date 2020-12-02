package otus.java.gk;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class GcDemo {
    private final int batchSize;
    private final int shift;
    private final int timeout;
    private long previousStart;
    private int startIndex;
    private List<UUID> storage;

    public GcDemo(int batchSize, int shift, int timeout) {
        this.batchSize = batchSize;
        this.shift = shift;
        this.timeout = timeout;
        this.storage = new ArrayList<>();
    }

    public void start() throws InterruptedException {
        displayPid();
        switchOnMonitoring();
        addMemoryLeak();
    }

    private void displayPid() {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
    }

    private void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    System.out.println((startTime - previousStart) + "ms|"  + gcName + "|" + gcAction + "|"
                            + gcCause + "(" + duration + " ms)|" + startIndex + "|" + storage.size());
                    previousStart = startTime;
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

    private void addMemoryLeak() throws InterruptedException {
        int threshold = Integer.MAX_VALUE;
        boolean stop = false;
        while (!stop) {
            for (int i = 0; i < batchSize; i++) {
                if (storage.size() == threshold) {
                    stop = true;
                    break;
                }
                int index = startIndex + i;
                if (index < storage.size()) {
                    storage.set(index, UUID.randomUUID());
                } else {
                    storage.add(UUID.randomUUID());
                }
            }
            TimeUnit.MICROSECONDS.sleep(timeout);
            startIndex += shift;
        }
    }
}
