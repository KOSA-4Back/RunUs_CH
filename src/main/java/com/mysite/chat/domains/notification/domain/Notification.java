package com.mysite.chat.domains.notification.domain;

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

/**
 * packageName    : com.mysite.chat.domains.alram.domain
 * fileName       : Allarm
 * author         : Yeong-Huns
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        Yeong-Huns       최초 생성
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    private long senderId;
    private String senderName;
    private String senderProfile;
    private long receiverId;
    private String content;
    private boolean isRead;
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Notification(long senderId, long receiverId, String content, String senderName, String senderProfile) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.senderName = senderName;
        this.senderProfile = senderProfile;
        this.isRead = false;
    }

    public Notification updateIsReadTrue(){
        this.isRead = true;
        return this;
    }
}
