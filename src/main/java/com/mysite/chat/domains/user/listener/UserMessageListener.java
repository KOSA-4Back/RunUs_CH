package com.mysite.chat.domains.user.listener;

import com.mysite.chat.domains.chatRoom.service.ChatRoomService;
import com.mysite.chat.domains.message.domain.Message;
import com.mysite.chat.domains.message.dto.response.ReceivedMessage;
import com.mysite.chat.domains.notification.domain.Notification;
import com.mysite.chat.domains.notification.repository.NotificationRepository;
import com.mysite.chat.global.redis.service.RedisUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * packageName    : com.mysite.chat.domains.user.listener
 * fileName       : UserMessageListener
 * author         : Yeong-Huns
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        Yeong-Huns       최초 생성
 */

@Log4j2
public class UserMessageListener {
    private final RedisUserService redisUserService;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;
    private final ChatRoomService chatRoomService;
    private final String userId;

    public UserMessageListener(RedisUserService redisUserService, SimpMessagingTemplate messagingTemplate, NotificationRepository notificationRepository, ChatRoomService chatRoomService, String userId) {
        this.redisUserService = redisUserService;
        this.messagingTemplate = messagingTemplate;
        this.notificationRepository = notificationRepository;
        this.chatRoomService = chatRoomService;
        this.userId = userId;
    }

    public void handleMessage(Message message) {
        // Redis에서 해당 사용자가 접속 중인지 확인
        String isConnected = redisUserService.getUserConnectionStatus(userId);
        if (isConnected.equals("true")) {
            messagingTemplate.convertAndSendToUser(userId, "/queue/message", message);
        } else {
            saveNotification(userId, ReceivedMessage.of(ReceivedMessage.from(message), Integer.parseInt(userId)));
        }
    }

    private void saveNotification(String username, Notification message) {
        // 알림 저장 로직 (MongoDB 등)
        notificationRepository.save(message);
    }
}