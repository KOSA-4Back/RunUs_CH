package com.mysite.chat.domains.message.listener;

import com.mysite.chat.domains.chatRoom.service.ChatRoomService;
import com.mysite.chat.domains.message.domain.Message;
import com.mysite.chat.domains.message.dto.response.ReceivedMessage;
import com.mysite.chat.domains.notification.domain.Notification;
import com.mysite.chat.domains.notification.repository.NotificationRepository;
import com.mysite.chat.global.redis.service.RedisUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.mysite.chat.domains.message.listener
 * fileName       : ChatMessageListener
 * author         : Yeong-Huns
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        Yeong-Huns       최초 생성
 */
@RequiredArgsConstructor
@Service
public class ChatMessageListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisUserService redisUserService;
    private final NotificationRepository notificationRepository;
    private final ChatRoomService chatRoomService;



    public void receiveMessage(Message message, @Header("amqp_consumerQueue") String queue) {
        String[] parts = queue.split("\\.");
        String username = parts[2];
        String chatRoomId = parts[3];

        // Redis에서 해당 사용자가 접속 중인지 확인
        String isConnected = redisUserService.getUserConnectionStatus(username);

        if ("true".equals(isConnected)) {
            messagingTemplate.convertAndSendToUser(username, "/queue/messages/" + chatRoomId, message);
        } else {
            // 화면 상단에 작은 모달창으로 메시지 도착 알림
            // MongoDB에 알람 저장
            saveNotification(username, ReceivedMessage.of(ReceivedMessage.from(message), Integer.parseInt(username)));
            // 시간 남으면 Notification DTO 로 리팩토링
        }
    }

    private void saveNotification(String username, Notification message) {
        // 알림 저장 로직 (MongoDB 등)
        notificationRepository.save(message);
    }

}
