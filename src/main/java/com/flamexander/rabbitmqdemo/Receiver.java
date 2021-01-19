package com.flamexander.rabbitmqdemo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void receiveMessageFromTopic(String message) {
        System.out.println("Received from topic <" + message + ">");
    }

    public void receiveMessageFromDirect(String message) {
        System.out.println("Received from direct <" + message + ">");
       rabbitTemplate.convertAndSend(RabbitmqDemoApplication.topicExchangeName, "foo.bar.baz", "Confirmed from direct!");
    }
}
