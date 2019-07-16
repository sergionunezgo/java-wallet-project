package com.sergio.wallet.client.services;

import org.springframework.stereotype.Service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import io.grpc.StatusRuntimeException;

import org.sergio.grpc.HelloServiceGrpc.HelloServiceBlockingStub;
import org.sergio.grpc.HelloRequest;
import org.sergio.grpc.HelloResponse;

@Service
public class GrpcHelloService {

    @GrpcClient("local-grpc-server")
    private HelloServiceBlockingStub helloStub;

    public String sendHelloMessage(final String firstName, final String lastName) {
        final HelloRequest request = HelloRequest
                                    .newBuilder()
                                    .setFirstName(firstName)
                                    .setLastName(lastName)
                                    .build();

        try {

            final HelloResponse response = this.helloStub.hello(request);
            return response.getGreeting();
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }
}