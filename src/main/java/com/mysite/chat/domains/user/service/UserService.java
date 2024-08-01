package com.mysite.chat.domains.user.service;

import com.mysite.chat.domains.chatRoom.service.ChatRoomService;
import com.mysite.chat.domains.notification.repository.NotificationRepository;
import com.mysite.chat.domains.user.Repository.UserRepository;
import com.mysite.chat.domains.user.domain.UserAddress;
import com.mysite.chat.domains.user.dto.AddressRequest;
import com.mysite.chat.domains.user.listener.UserMessageListener;
import com.mysite.chat.global.error.exception.NotFoundException;
import com.mysite.chat.global.redis.service.RedisUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public void addAddress(AddressRequest request){
        userRepository.save(request.toEntity().addChatRoom(request.address()));
    }

    public void removeAddress(AddressRequest request){
        userRepository.save(request.toEntity().removeChatRoom(request.address()));
    }

    public List<String> getAllAddress(long userId){
        return userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("못찾아요"))
                .getChatRoomAddress();
    }

    private boolean findById(AddressRequest request){
        return userRepository.existsById(request.userId());
    }


    // 생성
    private void createUser(UserAddress userAddress) {
        userRepository.save(userAddress);
        Queue personalQueue = new Queue("personal."+ userAddress.getId());
        rabbitAdmin.declareQueue(personalQueue);

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("personal."+ userAddress.getId());
        container.setMessageListener(new MessageListenerAdapter(new UserMessageListener(redisUserService, messagingTemplate, notificationRepository, chatRoomService, ""+ userAddress.getId()), "handleMessage"));
        container.start();
    }
    // 업데이트
    private void updateUser(UserAddress userAddress) {
        userRepository.save(userAddress);
    }
    // 삭제
    private void deleteUser(UserAddress userAddress) {
        userRepository.delete(userAddress);
        rabbitAdmin.deleteQueue("personal."+ userAddress.getId());
    }
}
