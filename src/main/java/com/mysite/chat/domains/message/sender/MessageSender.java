package com.mysite.chat.domains.message.sender;

import com.mysite.chat.domains.message.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * packageName    : com.mysite.chat.domains.message.sender
 * fileName       : MessageSender
 * author         : Yeong-Huns
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        Yeong-Huns       최초 생성
 */
@RequiredArgsConstructor
public class MessageSender {
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange messageExchange;
    public void sendMessage(Message message){
        String routingKey = "room."+message.getChatRoomId();
        rabbitTemplate.convertAndSend("msg.ex", routingKey, message);
    }

}
