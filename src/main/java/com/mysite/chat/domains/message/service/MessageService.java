package com.mysite.chat.domains.message.service;

import com.mysite.chat.domains.chatRoom.repository.ChatRoomRepository;
import com.mysite.chat.domains.message.domain.Message;
import com.mysite.chat.domains.message.dto.request.CreateMessageRequest;
import com.mysite.chat.domains.message.repository.MessageRepository;
import com.mysite.chat.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    private final RabbitAdmin rabbitAdmin;
    private final RabbitTemplate rabbitTemplate;

    public void sendMessageToChatRoom(String ChatRoomId, Message message){
        String routingKey =  "room."+ChatRoomId;
        rabbitTemplate.convertAndSend("msg.ex", routingKey, message);
    }

    // 생성
    public Message CreateMessage(CreateMessageRequest request){

        return messageRepository.save(request.toEntity());
    }
   // 조회
    public Message findMessageById(String id){
        return messageRepository.findById(id).orElseThrow(()->new NotFoundException("해당 ID 의 메세지가 존재하지 않습니다."));
    }
   // chatRoomId 로 메세지 목록 조회 (참여 시점보다 이후 메세지만 조회)
    public List<Message> findAllMessageByChatRoomId(String chatRoomId, long userId){
        LocalDateTime joinDate = chatRoomRepository.findById(chatRoomId).orElseThrow(NotFoundException::new).getParticipantJoinedAt(userId).orElseThrow(NotFoundException::new);
        return findMessagesAfterJoinDate(chatRoomId, joinDate);
    }
    // chatRoomId 와 userId 로 해당 채팅방의

    public List<Message> findMessagesAfterJoinDate(String chatRoomId, LocalDateTime joinDate) {
        // 참여 시점 이후의 메시지 조회
        Query query = new Query(
                Criteria.where("chatRoomId").is(chatRoomId)
                        .and("createdAt").gte(joinDate)
        ).with(Sort.by(Sort.Direction.ASC, "createdAt")); // 생성 시간 순으로 정렬

        return mongoTemplate.find(query, Message.class);
    }

   // 메세지 삭제? optional
    public void deleteMessageById(String id){
        messageRepository.deleteById(id);
    }
   // 메세지 수신 처리
    public Message updateReadCountById(String id){
        Message message = messageRepository.findById(id).orElseThrow(()->new NotFoundException("해당 메세지를 찾을 수 없습니다."))
                .updateUnReadCount();
        return messageRepository.save(message);
    }

}
