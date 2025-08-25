package com.wifi32767.infra.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RocketEventPublisher implements EventPublisher {

    RocketMQTemplate rocketMQTemplate;

    @Value("${spring.rocketmq.config.producer.topic}")
    private String topicName;

    public void publish(String tag, String message) {
        try {
            String key = topicName + ":" + tag;
            rocketMQTemplate.convertAndSend(key, message);
        } catch (Exception e) {
            log.error("发送MQ消息失败 message:{}", message, e);
            throw e;
        }
    }
}
