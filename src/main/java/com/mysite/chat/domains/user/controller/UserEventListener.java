package com.mysite.chat.domains.user.controller;

import com.mysite.chat.domains.user.service.UserService;
import com.mysite.chat.domains.user.dto.UserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.mysite.chat.global.rabbitMQ.user.listener
 * fileName       : UserEventListener
 * author         : Yeong-Huns
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        Yeong-Huns       최초 생성
 */
@RequiredArgsConstructor
@Component
public class UserEventListener {
    private final UserService userService;

    @RabbitListener(queues = "user.event.queue")
    public void handleUserEvent(UserEvent userEvent) {
        userService.handleUserEvent(userEvent);
    }
}
