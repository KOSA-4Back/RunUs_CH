package com.mysite.chat.domains.user.service;

import com.mysite.chat.domains.chatRoom.service.ChatRoomService;
import com.mysite.chat.domains.notification.repository.NotificationRepository;
import com.mysite.chat.domains.user.Repository.UserRepository;
import com.mysite.chat.domains.user.domain.MongoUser;
import com.mysite.chat.domains.user.dto.UserEvent;
import com.mysite.chat.domains.user.listener.UserMessageListener;
import com.mysite.chat.global.redis.service.RedisUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


/**
 * packageName    : com.mysite.chat.global.rabbitMQ.service
 * fileName       : RabbitMQService
 * author         : Yeong-Huns
 * date           : 2024-07-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-30        Yeong-Huns       최초 생성
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final RabbitAdmin rabbitAdmin;
    private final UserRepository userRepository;
    private final ConnectionFactory connectionFactory;
    private final RedisUserService redisUserService;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationRepository notificationRepository;
    private final ChatRoomService chatRoomService;

    public void handleUserEvent(UserEvent event) {
        switch (event.type()) {
            case "CREATE":
                createUser(event.user().toEntity());
                break;
            case "UPDATE":
                updateUser(event.user().toEntity());
                break;
            case "DELETE":
                deleteUser(event.user().toEntity());
                break;
        }
    }
    // 생성
    private void createUser(MongoUser mongoUser) {
        userRepository.save(mongoUser);
        Queue personalQueue = new Queue("personal."+ mongoUser.getId());
        rabbitAdmin.declareQueue(personalQueue);

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("personal."+ mongoUser.getId());
        container.setMessageListener(new MessageListenerAdapter(new UserMessageListener(redisUserService, messagingTemplate, notificationRepository, chatRoomService, ""+mongoUser.getId()), "handleMessage"));
        container.start();
    }
    // 업데이트
    private void updateUser(MongoUser mongoUser) {
        userRepository.save(mongoUser);
    }
    // 삭제
    private void deleteUser(MongoUser mongoUser) {
        userRepository.delete(mongoUser);
        rabbitAdmin.deleteQueue("personal."+ mongoUser.getId());
    }
}
