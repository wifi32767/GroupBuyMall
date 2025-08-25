package com.wifi32767.infra.event;

public interface EventPublisher {

    void publish(String routingKey, String message);

}
