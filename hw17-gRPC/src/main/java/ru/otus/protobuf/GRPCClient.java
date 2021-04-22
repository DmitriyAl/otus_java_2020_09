package ru.otus.protobuf;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.RemoteNumberServiceGrpc;
import ru.otus.protobuf.generated.RequestedRange;
import ru.otus.protobuf.observer.NumberStreamObserver;

import java.util.concurrent.TimeUnit;

public class GRPCClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8090;
    private static final int MIN = 0;
    private static final int MAX = 30;
    private static final int DELAY = 1;
    private static int currentNumber;
    private static final Logger log = LoggerFactory.getLogger(GRPCClient.class);

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();
        RemoteNumberServiceGrpc.RemoteNumberServiceStub stub = RemoteNumberServiceGrpc.newStub(channel);
        final NumberStreamObserver numberStreamObserver = new NumberStreamObserver();
        stub.getNumber(RequestedRange.newBuilder().setMin(MIN).setMax(MAX).build(), numberStreamObserver);
        for (int i = 0; i <= 50; i++) {
            currentNumber = currentNumber + numberStreamObserver.getValue() + 1;
            log.info("currentValue: {}", currentNumber);
            sleep();
        }
        channel.shutdown();
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}