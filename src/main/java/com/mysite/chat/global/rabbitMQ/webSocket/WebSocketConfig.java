package com.mysite.chat.global.rabbitMQ.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * packageName    : com.mysite.chat.global.rabbitMQ.config
 * fileName       : WebSocketConfig
 * author         : Yeong-Huns
 * date           : 2024-07-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-30        Yeong-Huns       최초 생성
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 연결 포인트(endpoint) 설정
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:3000/", "http://localhost:8080", "http://192.168.0.22:3000", "http://192.168.230.30:3000/runus/", "http://localhost:3000/runus")
                .withSockJS(); // SockJS 지원을 활성화하여, 웹소켓이 지원되지 않는 브라우저에서도 사용 가능
    }
}
