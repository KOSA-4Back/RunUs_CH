package com.mysite.chat.domains.chatRoom.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mysite.chat.domains.message.domain.Message;
import com.mysite.chat.global.mongo.ObjectIdSerializer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * packageName    : com.mysite.chat.domains.chatRoom.domain
 * fileName       : ChatRoom
 * author         : Yeong-Huns
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        Yeong-Huns       최초 생성
 */
@Getter
@Document(collection = "chatRooms")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {
    @Id
    private String id;
    private String title;
    private List<Participant> participants;
    private long createdBy;
    private Message lastMessage;
    @CreatedDate // 몽고DB Audit
    private LocalDateTime createdAt;
    @LastModifiedDate // 몽고DB Audit
    private LocalDateTime updatedAt;

    @Builder
    public ChatRoom(String title, long createdBy) {
        this.title = title;
        this.createdBy = createdBy;
        this.participants = new ArrayList<>(List.of(Participant.from(createdBy)));
    }

    // 채팅방 입장
    public ChatRoom addParticipant(long userId) {
        if(!participants.contains(Participant.from(userId))) // 없으면 추가해라
            participants.add(Participant.from(userId));
        return this;
    }

    // 채팅방 탈퇴
    public ChatRoom removeParticipant(long userId) {
        participants.remove(Participant.from(userId));
        return this;
    }

    // 참여 시간 조회
    public Optional<LocalDateTime> getParticipantJoinedAt(long userId) {
        return participants.stream()
                .filter(participant->participant.getUserId() == userId)
                .map(Participant::getJoinedAt)
                .findFirst();
    }
    // 큐 네임 반환
    public List<String> getQueueNames(){
        return this.participants.stream().map(userId->"personal."+userId).toList();
    }

    // 마지막 메세지 업데이트
    public ChatRoom updateLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
        return this;
    }

}
