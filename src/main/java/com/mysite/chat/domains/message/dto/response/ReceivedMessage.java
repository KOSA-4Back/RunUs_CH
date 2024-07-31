package com.mysite.chat.domains.message.dto.response;

import com.mysite.chat.domains.message.domain.Message;
import com.mysite.chat.domains.notification.domain.Notification;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * packageName    : com.mysite.chat.domains.message.dto.response
 * fileName       : ReceivedMessage
 * author         : Yeong-Huns
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        Yeong-Huns       최초 생성
 */
@Builder
public record ReceivedMessage(
        String id,
        String chatRoomId,
        long senderId,
        String senderNickName,
        String senderProfileUrl,
        String content,
        int unReadCount,
        LocalDateTime createdAt
) {
    public static Notification of(ReceivedMessage receivedMessage ,long receiverId){
        return Notification.builder()
                .senderId(receivedMessage.senderId)
                .senderName(receivedMessage.senderNickName)
                .senderProfile(receivedMessage.senderProfileUrl)
                .receiverId(receiverId)
                .content(receivedMessage.content)
                .build();
    }

    public static ReceivedMessage from(Message message){
        return ReceivedMessage.builder()
                .id(message.getId())
                .chatRoomId(message.getChatRoomId())
                .senderId(message.getSenderId())
                .senderNickName(message.getSenderNickName())
                .senderProfileUrl(message.getSenderProfileUrl())
                .content(message.getContent())
                .unReadCount(message.getUnReadCount() - 1)
                .createdAt(message.getCreatedAt())
                .build();
    }
}
