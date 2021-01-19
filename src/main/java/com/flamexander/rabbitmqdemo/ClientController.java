package com.flamexander.rabbitmqdemo;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@RestController
public class ClientController {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public String sendMessage() {
        System.out.println("Sending message...");
//        rabbitTemplate.convertAndSend(RabbitmqDemoApplication.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
//        rabbitTemplate.convertAndSend(RabbitmqDemoApplication.directExchangeName,"PDF Process", "Hello PDF");
        rabbitTemplate.convertAndSend("spring-boot-topic-queue","", "Hello PDF");
        return "OK";
    }
}
