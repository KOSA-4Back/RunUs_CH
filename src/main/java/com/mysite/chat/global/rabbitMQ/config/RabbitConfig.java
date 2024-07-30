package com.mysite.chat.global.rabbitMQ.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mysite.chat.domains.user.controller.UserEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.mysite.chat.global.rabbitMQ.config
 * fileName       : RabbitConfig
 * author         : Yeong-Huns
 * date           : 2024-07-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-30        Yeong-Huns       최초 생성
 */
@RequiredArgsConstructor
@Configuration
@EnableRabbit
public class RabbitConfig {
    public static final String USER_EXCHANGE_NAME = "user.ex";
    public static final String MESSAGE_EXCHANGE_NAME = "msg.ex";
    public static final String USER_EVENT_QUEUE = "user.event.queue";
    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(CachingConnectionFactory connectionFactory,
                                                                   MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("user.event.queue");
        container.setMessageListener(listenerAdapter);
        return container;
    }
    @Bean
    public MessageListenerAdapter listenerAdapter(UserEventListener receiver) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, "handleUserEvent");
        adapter.setMessageConverter(jsonMessageConverter());
        return adapter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(CachingConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public DirectExchange userExchange(){ //유저 생성 , 삭제, 수정용
        return new DirectExchange(USER_EXCHANGE_NAME, true, false);
    }

    @Bean
    public TopicExchange MessageExchange(){ //메세지, 알람용
        return new TopicExchange(MESSAGE_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue userEventQueue(){
        return new Queue(USER_EVENT_QUEUE, true);
    }

    @Bean
    public Binding userEventBinding(DirectExchange userExchange, Queue userEventQueue) {
        return BindingBuilder.bind(userEventQueue).to(userExchange).with("user.event");
    }


/*
    public Exchange createExchange(String exchangeName, ExchangeType exchangeType, RabbitAdmin rabbitAdmin) {
        Exchange exchange = exchangeType.createExchange(exchangeName);
        rabbitAdmin.declareExchange(exchange);
        return exchange;
    }*/
/*public Queue createQueue(String queueName, RabbitAdmin rabbitAdmin) {
    Queue queue = new Queue(queueName, true);
    rabbitAdmin.declareQueue(queue);
    return queue;
}*/
//
//    public void deleteQueue(String queueName, RabbitAdmin rabbitAdmin) {
//        rabbitAdmin.deleteQueue(queueName);
//    }
//
//    public Binding createBinding(Queue queue, Exchange exchange, String routingKey, ExchangeType exchangeType, RabbitAdmin rabbitAdmin) {
//        Binding binding = exchangeType.createBinding(queue, exchange, routingKey);
//        rabbitAdmin.declareBinding(binding);
//        return binding;
//    }
}
