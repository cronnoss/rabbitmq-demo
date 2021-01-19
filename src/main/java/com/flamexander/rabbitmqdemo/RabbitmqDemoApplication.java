package com.flamexander.rabbitmqdemo;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitmqDemoApplication {
    static final String topicExchangeName = "spring-boot-exchange-topic";
    static final String directExchangeName = "spring-boot-exchange-direct";

    static final String queueTopicName = "spring-boot-topic-queue";
    static final String queueDirectName = "spring-boot-direct-queue";

    @Bean
    Queue queueTopic() {
        return new Queue(queueTopicName, false);
    }

    @Bean
    Queue queueDirect() {
        return new Queue(queueDirectName, false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(directExchangeName);
    }

    @Bean
    Binding bindingTopic(@Qualifier("queueTopic") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("foo.bar.#");
    }

    @Bean
    Binding bindingDirect(@Qualifier("queueDirect") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("PDF Process");
    }

    @Bean
    SimpleMessageListenerContainer containerForTopic(ConnectionFactory connectionFactory, @Qualifier("listenerAdapterForTopic") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueTopicName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer containerForDirect(ConnectionFactory connectionFactory, @Qualifier("listenerAdapterForDirect") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueDirectName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapterForTopic(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessageFromTopic");
    }

    @Bean
    MessageListenerAdapter listenerAdapterForDirect(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessageFromDirect");
    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqDemoApplication.class, args);
    }
}
