package com.mysite.chat.domains.notification.dto.request;

import com.mysite.chat.domains.notification.domain.Notification;
import lombok.Builder;

/**
 * packageName    : com.mysite.chat.domains.notification.dto.request
 * fileName       : CreateNotificationRequest
 * author         : Yeong-Huns
 * date           : 2024-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        Yeong-Huns       최초 생성
 */
@Builder
public record CreateNotificationRequest(int senderId, int receiverId, String content) {
    public Notification toEntity(){
        return Notification.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .content(content)
                .build();
    }
}
