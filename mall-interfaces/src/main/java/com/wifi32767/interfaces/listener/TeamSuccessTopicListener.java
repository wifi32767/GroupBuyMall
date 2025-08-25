package com.wifi32767.interfaces.listener;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        topic = "${rocketmq.config.topic-team-success}",
        consumerGroup = "${rocketmq.config.consumer-group:team-success-consumer-group}",
        selectorExpression = "*"
)
public class TeamSuccessTopicListener implements RocketMQListener<String> {

    public void onMessage(String message) {
        log.info("接收消息:{}", message);
    }
}
