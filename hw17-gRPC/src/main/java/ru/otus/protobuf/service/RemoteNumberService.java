package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.RemoteNumberServiceGrpc;
import ru.otus.protobuf.generated.RequestedRange;
import ru.otus.protobuf.generated.ResponseNumber;

import java.util.concurrent.TimeUnit;

public class RemoteNumberService extends RemoteNumberServiceGrpc.RemoteNumberServiceImplBase {
    private final int delay = 2;
    private static final Logger log = LoggerFactory.getLogger(RemoteNumberService.class);

    public RemoteNumberService() {
    }

    @Override
    public void getNumber(RequestedRange request, StreamObserver<ResponseNumber> responseObserver) {
        final int min = request.getMin();
        final int max = request.getMax();
        for (int i = min; i <= max; i++) {
            sleep();
            log.info("server sent {}", i);
            responseObserver.onNext(ResponseNumber.newBuilder().setValue(i).build());
        }
        responseObserver.onCompleted();
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}