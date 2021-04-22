package ru.otus.protobuf;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.otus.protobuf.service.RemoteNumberService;

import java.io.IOException;

public class GRPCServer {
    public static final int SERVER_PORT = 8090;

    public static void main(String[] args) throws InterruptedException, IOException {
        final RemoteNumberService remoteNumberService = new RemoteNumberService();
        final Server server = ServerBuilder.forPort(SERVER_PORT).addService(remoteNumberService).build();
        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}