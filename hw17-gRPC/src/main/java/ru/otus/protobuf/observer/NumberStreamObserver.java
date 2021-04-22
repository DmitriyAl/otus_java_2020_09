package ru.otus.protobuf.observer;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.ResponseNumber;

public class NumberStreamObserver implements StreamObserver<ResponseNumber> {
    private int lastValue;
    private boolean updated;
    private static final Logger log = LoggerFactory.getLogger(NumberStreamObserver.class);

    @Override
    public synchronized void onNext(ResponseNumber value) {
        log.info("new value: {}", value.getValue());
        lastValue = value.getValue();
        updated = true;
    }

    @Override
    public void onError(Throwable t) {
        log.error("values stream sent an error", t);
    }

    @Override
    public void onCompleted() {
        log.info("server values stream is finished");
    }

    public synchronized int getValue() {
        if (updated) {
            updated = false;
            return lastValue;
        }
        return 0;
    }
}