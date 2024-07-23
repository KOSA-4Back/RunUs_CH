package com.mysite.chat.global.rabbitMQ.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.mysite.chat.global.rabbitMQ.config
 * fileName       : ChatServerConfig
 * author         : Yeong-Huns
 * date           : 2024-07-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-20        Yeong-Huns       최초 생성
 */
@Configuration
public class ChatServerConfig {
    @Bean
    public Queue userCreateQueue(){
        return new Queue("member.create.queue", true);
    }
    @Bean
    public Queue userDeleteQueue(){
        return new Queue("member.delete.queue", true);
    }
    @Bean
    public Queue userUpdateQueue(){
        return new Queue("member.update.queue", true);
    }

    @Bean
    public Queue userDeleteAllQueue(){
        return new Queue("member.delete.all.queue", true);
    }

    @Bean
    public Binding bindingCreateQueue(DirectExchange exchange, Queue userCreateQueue){
        return BindingBuilder.bind(userCreateQueue).to(exchange).with("member.create");
    }

    @Bean
    public Binding bindingDeleteQueue(DirectExchange exchange, Queue userDeleteQueue){
        return BindingBuilder.bind(userDeleteQueue).to(exchange).with("member.delete");
    }

    @Bean
    public Binding bindingUpdateQueue(DirectExchange exchange, Queue userUpdateQueue){
        return BindingBuilder.bind(userUpdateQueue).to(exchange).with("member.update");
    }

    @Bean
    public Binding bindingDeleteAllQueue(DirectExchange exchange, Queue userDeleteAllQueue){
        return BindingBuilder.bind(userDeleteAllQueue).to(exchange).with("member.delete.all");
    }
}
