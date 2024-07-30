package com.mysite.chat.global.rabbitMQ.webSocket;

import com.mysite.chat.global.redis.service.RedisUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * packageName    : com.mysite.chat.global.rabbitMQ.webSocket
 * fileName       : WebSocketEventListener
 * author         : Yeong-Huns
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        Yeong-Huns       최초 생성
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final RedisUserService redisUserService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String userId = headerAccessor.getFirstNativeHeader("userId");
        if(userId != null && sessionId != null) {
            redisUserService.updateUserConnectionStatus(userId, true);
            redisUserService.saveSessionKey(sessionId, userId);
            log.info("웹 소켓 연결 감지 : {}", userId);
        }
    }
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String userId = redisUserService.getUserIdBySessionKey(sessionId);
        if(userId != null){
            redisUserService.updateUserConnectionStatus(userId, false);
            redisUserService.deleteSession(sessionId); // 세션 정보 삭제
            log.info("웹 소켓 연결 끊어짐 감지 : {}", userId);
        }
    }

}
