package com.mysite.chat.global.rabbitMQ;

import org.springframework.amqp.core.*;

/**
 * packageName    : com.mysite.chat.global.rabbitMQ
 * fileName       : ExchangeType
 * author         : Yeong-Huns
 * date           : 2024-07-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-30        Yeong-Huns       최초 생성
 */


public enum ExchangeType {
    DIRECT {
        @Override
        public Exchange createExchange(String name) {
            return new DirectExchange(name);
        }

        @Override
        public Binding createBinding(Queue queue, Exchange exchange, String routingKey) {
            return BindingBuilder.bind(queue).to((DirectExchange) exchange).with(routingKey);
        }
    },
    TOPIC {
        @Override
        public Exchange createExchange(String name) {
            return new TopicExchange(name);
        }

        @Override
        public Binding createBinding(Queue queue, Exchange exchange, String routingKey) {
            return BindingBuilder.bind(queue).to((TopicExchange) exchange).with(routingKey);
        }
    },
    FANOUT {
        @Override
        public Exchange createExchange(String name) {
            return new FanoutExchange(name);
        }

        @Override
        public Binding createBinding(Queue queue, Exchange exchange, String routingKey) {
            return BindingBuilder.bind(queue).to((FanoutExchange) exchange);
        }
    },
    HEADERS {
        @Override
        public Exchange createExchange(String name) {
            return new HeadersExchange(name);
        }

        @Override
        public Binding createBinding(Queue queue, Exchange exchange, String routingKey) {
            return BindingBuilder.bind(queue).to((HeadersExchange) exchange).where(routingKey).exists();
        }
    };

    public abstract Exchange createExchange(String name);

    public abstract Binding createBinding(Queue queue, Exchange exchange, String routingKey);
}