package com.mysite.chat.domains.message.service;

import com.mysite.chat.domains.chatRoom.repository.ChatRoomRepository;
import com.mysite.chat.domains.message.domain.Message;
import com.mysite.chat.domains.message.dto.request.CreateMessageRequest;
import com.mysite.chat.domains.message.repository.MessageRepository;
import com.mysite.chat.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : com.mysite.chat.domains.message.service
 * fileName       : MessageService
 * author         : Yeong-Huns
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        Yeong-Huns       최초 생성
 */
@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MongoTemplate mongoTemplate;
   // 생성
    public Message CreateMessage(CreateMessageRequest request){
        return messageRepository.save(request.toEntity());
    }
   // 조회
    public Message findMessageById(ObjectId id){
        return messageRepository.findById(id).orElseThrow(()->new NotFoundException("해당 ID 의 메세지가 존재하지 않습니다."));
    }
   // chatRoomId 로 메세지 목록 조회 (참여 시점보다 이후 메세지만 조회)
    public List<Message> findAllMessageByChatRoomId(ObjectId chatRoomId, long userId){
        LocalDateTime joinedAt = chatRoomRepository.findParticipantJoinedAt(mongoTemplate, chatRoomId, userId).joinedAt(); // 해당 user 의 joined date 를 구한다.
        return messageRepository.findAllByChatRoomIdAndCreatedAtAfterOrderByCreatedAtDesc(chatRoomId, joinedAt);
    }
    // chatRoomId 와 userId 로 해당 채팅방의
   // 메세지 삭제? optional
    public void deleteMessageById(ObjectId id){
        messageRepository.deleteById(id);
    }
   // 메세지 수신 처리
    public Message updateReadCountById(ObjectId id){
        Message message = messageRepository.findById(id).orElseThrow(()->new NotFoundException("해당 메세지를 찾을 수 없습니다."))
                .updateUnReadCount();
        return messageRepository.save(message);
    }

}
