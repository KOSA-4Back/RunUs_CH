package com.mysite.chat.domains.chatRoom.controller;

import com.mysite.chat.domains.chatRoom.domain.ChatRoom;
import com.mysite.chat.domains.chatRoom.dto.request.AddOrRemoveParticipantRequest;
import com.mysite.chat.domains.chatRoom.dto.request.CreateChatRoomRequest;
import com.mysite.chat.domains.chatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.mysite.chat.domains.chatRoom.controller
 * fileName       : ChatRoomController
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
@RequestMapping("api/chatRoom")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping
    public ChatRoom createChatRoom(@RequestBody CreateChatRoomRequest request) {
        return chatRoomService.createChatsRoom(request);
    }

    @GetMapping("/{id}")
    public ChatRoom getChatRoomById(@PathVariable String id) {
        return chatRoomService.findChatRoomById(id);
    }
    @GetMapping("/all/chatRoom")
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomService.findChatRoomAll();
    }

    @GetMapping("/user/{userId}")
    public List<ChatRoom> getChatRoomsByUserId(@PathVariable long userId) {
        return chatRoomService.findChatRoomListByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public void deleteChatRoomById(@PathVariable String id) {
        chatRoomService.deleteChatRoomById(id);
    }

    @PostMapping("/participants")
    public void addParticipantToChatRoom(@RequestBody AddOrRemoveParticipantRequest request) {
        chatRoomService.addParticipantToChatRoom(request.chatRoomId(), request.userId());
    }

    @DeleteMapping("/participants")
    public void removeParticipantFromChatRoom(@RequestBody AddOrRemoveParticipantRequest request) {
        chatRoomService.removeParticipantFromChatRoom(request.chatRoomId(), request.userId());
    }
}
