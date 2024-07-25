package com.mysite.chat.domains.notification.domain;

import lombok.AccessLevel;
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
    private ObjectId id;
    private int senderId;
    private int receiverId;
    private String content;
    private boolean isRead;
    @CreatedDate
    private LocalDateTime createdAt;
}
