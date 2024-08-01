package com.mysite.chat.global.rabbitMQ.config;

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
        // 타입 정보 없이 Jackson JSON 컨버터를 생성합니다.
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        return converter;
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    // 메시지 리스너 어댑터를 설정합니다. 이 어댑터는 수신된 메시지를 특정 메소드(handleUserEvent)로 라우팅합니다.



    // RabbitMQ 관리를 위한 RabbitAdmin 빈을 설정합니다.
    @Bean
    public RabbitAdmin rabbitAdmin(CachingConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    // DirectExchange 설정. 주로 유저 생성, 삭제, 수정 이벤트를 처리합니다.
    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange(USER_EXCHANGE_NAME, true, false);
    }

    // TopicExchange 설정. 메시지 및 알람 이벤트 처리를 위해 사용됩니다.
    @Bean
    public TopicExchange messageExchange() {
        return new TopicExchange(MESSAGE_EXCHANGE_NAME, true, false);
    }

    @Bean
    public DirectExchange msgExchange(){
        return new DirectExchange("topic.message", true, false);
    }

    @Bean DirectExchange alarmExchange(){
        return  new DirectExchange("topic.alarm", true, false);
    }

    @Bean Queue msgExchangeQueue(){
        return new Queue("topic.message", true );
    }
    @Bean Queue alarmExchangeQueue(){
        return new Queue("topic.alarm", true );
    }

    // 유저 이벤트를 위한 큐를 정의합니다.
    @Bean
    public Queue userCreateQueue() {
        return new Queue("user.event.create", true);
    }
    @Bean
    public Binding userCreateEventBinding(DirectExchange userExchange, Queue userCreateQueue) {
        return BindingBuilder.bind(userCreateQueue).to(userExchange).with("user.event.create");
    }
    @Bean
    public Queue userUpdateQueue(){
        return new Queue("user.event.update", true);
    }
    @Bean
    public Binding userUpdateEventBinding(DirectExchange userExchange, Queue userUpdateQueue) {
        return BindingBuilder.bind(userUpdateQueue).to(userExchange).with("user.event.update");
    }
    // 유저 이벤트 큐와 DirectExchange를 바인딩합니다.


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
