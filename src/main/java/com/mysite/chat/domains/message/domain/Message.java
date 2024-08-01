package com.mysite.chat.domains.message.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mysite.chat.global.mongo.ObjectIdSerializer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * packageName    : com.mysite.chat.domains.message.domain
 * fileName       : Message
 * author         : Yeong-Huns
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        Yeong-Huns       최초 생성
 */
@Getter
@Document(collection = "messages")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    @Id
    private String id;
    private String chatRoomId;
    private long senderId;
    private String senderNickName;
    private String senderProfileUrl;
    private String content;
    private int unReadCount;
    @CreatedDate
    private LocalDateTime createdAt;
    @Builder
    public Message(String chatRoomId, String type, long senderId, String senderNickName, String senderProfileUrl, String content, int unReadCount) {
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.senderNickName = senderNickName;
        this.senderProfileUrl = senderProfileUrl;
        this.content = content;
        this.unReadCount = unReadCount;
    }

    public Message updateUnReadCount(){
        this.unReadCount--;
        return this;
    }
}
