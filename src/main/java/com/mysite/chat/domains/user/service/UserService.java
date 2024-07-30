package com.mysite.chat.domains.user.service;

import com.mysite.chat.domains.user.domain.MongoUser;
import com.mysite.chat.domains.user.Repository.UserRepository;
import com.mysite.chat.domains.user.dto.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
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
