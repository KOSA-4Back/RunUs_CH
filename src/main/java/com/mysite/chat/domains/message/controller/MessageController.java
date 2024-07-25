package com.mysite.chat.domains.message.controller;

import com.mysite.chat.domains.message.domain.Message;
import com.mysite.chat.domains.message.dto.request.CreateMessageRequest;
import com.mysite.chat.domains.message.dto.request.GetAllMessageRequest;
import com.mysite.chat.domains.message.service.MessageService;
import com.mysite.chat.global.error.errorCode.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public Message createMessage(@RequestBody CreateMessageRequest request) {
        return messageService.CreateMessage(request);
    }
    // 조회
    @GetMapping("/{id}")
    public Message getMessageById(@PathVariable ObjectId id) {
        return messageService.findMessageById(id);
    }
    // 특정 채팅방 메세지 리스트 조회
    @GetMapping("/chatRoom")
    public List<Message> getAllMessagesByChatRoomId(@RequestBody GetAllMessageRequest request) {
        return messageService.findAllMessageByChatRoomId(request.chatRoomId(), request.userId());
    }
    // 삭제 (optional0)
    @DeleteMapping("/{id}")
    public void deleteMessageById(@PathVariable ObjectId id) {
        messageService.deleteMessageById(id);
    }
    // 메세지 수신 처리
    @PutMapping("/read/{id}")
    public Message updateReadCountById(@PathVariable ObjectId id) {
        return messageService.updateReadCountById(id);
    }

}
