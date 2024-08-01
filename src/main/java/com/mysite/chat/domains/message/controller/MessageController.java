package com.mysite.chat.domains.message.controller;

import com.mysite.chat.domains.message.domain.Message;
import com.mysite.chat.domains.message.dto.request.CreateMessageRequest;
import com.mysite.chat.domains.message.dto.request.GetAllMessageRequest;
import com.mysite.chat.domains.message.dto.response.MessageFormat;
import com.mysite.chat.domains.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : com.mysite.chat.domains.message.controller
 * fileName       : MessageController
 * author         : Yeong-Huns
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        Yeong-Huns       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/message")
public class MessageController {

    private final MessageService messageService;
    // 생성

    @RabbitListener(queues = "topic.message")
    public void handleReceivedMessage(MessageFormat message) {
        messageService.handleReceivedMessage(message);
    }


    @PostMapping
    public Message createMessage(@RequestBody CreateMessageRequest request) {
        return messageService.CreateMessage(request);
    }
    // 조회
    @GetMapping("/{id}")
    public Message getMessageById(@PathVariable String id) {
        return messageService.findMessageById(id);
    }
    // 특정 채팅방 메세지 리스트 조회
    @GetMapping ("/chatRoom")
    public List<Message> getAllMessagesByChatRoomId(GetAllMessageRequest request) {
        return messageService.findAllMessageByChatRoomId(request.chatRoomId(), request.userId());
    }
    // 삭제 (optional0)
    @DeleteMapping("/{id}")
    public void deleteMessageById(@PathVariable String id) {
        messageService.deleteMessageById(id);
    }
    // 메세지 수신 처리
    @PutMapping("/read/{id}")
    public Message updateReadCountById(@PathVariable String id) {
        return messageService.updateReadCountById(id);
    }

    @GetMapping("/chat/test/dtd")
    public LocalDateTime testMethod(GetAllMessageRequest request){
        return messageService.getParticipantJoinedAt(request.chatRoomId(), request.userId());
    }

}
