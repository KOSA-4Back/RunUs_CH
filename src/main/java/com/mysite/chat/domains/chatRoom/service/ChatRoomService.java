package com.mysite.chat.domains.chatRoom.service;

import com.mysite.chat.domains.chatRoom.domain.ChatRoom;
import com.mysite.chat.domains.chatRoom.dto.request.CreateChatRoomRequest;
import com.mysite.chat.domains.chatRoom.repository.ChatRoomRepository;
import com.mysite.chat.domains.message.domain.Message;
import com.mysite.chat.domains.message.dto.response.GetParticipantsInfoResponse;
import com.mysite.chat.domains.user.Repository.UserRepository;
import com.mysite.chat.global.error.errorCode.ResponseCode;
import com.mysite.chat.global.error.exception.NotFoundException;
import com.mysite.chat.global.mongo.ObjectIdConverter;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : com.mysite.chat.domains.chatRoom.service
 * fileName       : ChatRoomService
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
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;
    private final TopicExchange messageExchange;
    private final RabbitAdmin rabbitAdmin;

    public void bindUserToChatRoom(String chatRoomId, long userId){
        String queueName = "personal."+userId;
        Binding binding = BindingBuilder.bind(new Queue(queueName)).to(messageExchange).with("room."+chatRoomId);
        rabbitAdmin.declareBinding(binding);
    }


    // 생성
    public ChatRoom createChatsRoom(CreateChatRoomRequest request) {
        return chatRoomRepository.save(request.toEntity());
    }

    // 조회
    public ChatRoom findChatRoomById(String id) {
        return chatRoomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_CHATROOM));
    }

    // userId 로 chatRoomList 조회
    public List<ChatRoom> findChatRoomListByUserId(long userId) {
        return chatRoomRepository.findAllByCreatedBy(userId);
    }

    // 삭제 (Optional)
    public void deleteChatRoomById(String id) {
        chatRoomRepository.deleteById(id);
    }

    // participant 추가 (채팅방 입장)
    @Transactional
    public void addParticipantToChatRoom(String chatRoomId, long userId) {
        userRepository.save(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("유저 못찾음.")).addChatRoom(chatRoomId));
        chatRoomRepository.save(findChatRoomById(chatRoomId).addParticipant(userId));
    }

    // participant 제거 (채팅방 탈퇴)
    public void removeParticipantFromChatRoom(String chatRoomId, long userId) {
        userRepository.save(userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("유저 못찾음.")).removeChatRoom(chatRoomId));
        chatRoomRepository.save(findChatRoomById(chatRoomId).removeParticipant(userId));
    }

    // unReadCount 측정


    // 다른 서비스로 이동 예정

    // 해당 user 의 채팅방 참여시점 조회
    public GetParticipantsInfoResponse getParticipantJoinedAt(String chatRoomId, long userId) {
        return chatRoomRepository.findParticipantJoinedAt(mongoTemplate, chatRoomId, userId);
    }

    // 마지막 메세지 업데이트
    public void updateLastMessage(String chatRoomId, Message lastMessage) {
        chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_CHATROOM))
                .updateLastMessage(lastMessage);
    }

    public List<String> getQueueNames(String chatRoomId){
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(NotFoundException::new)
                .getQueueNames();
    }

}
