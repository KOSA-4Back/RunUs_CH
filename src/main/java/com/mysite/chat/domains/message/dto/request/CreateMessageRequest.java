package com.mysite.chat.domains.message.dto.request;

import com.mysite.chat.domains.message.domain.Message;
import org.bson.types.ObjectId;

/**
 * packageName    : com.mysite.chat.domains.message.dto.request
 * fileName       : CreateMessageRequest
 * author         : Yeong-Huns
 * date           : 2024-07-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-25        Yeong-Huns       최초 생성
 */
public record CreateMessageRequest(
        String chatRoomId,
        long senderId,
        String senderNickName,
        String senderProfileUrl,
        String content,
        int unReadCount
) {
    public Message toEntity(){
        return Message.builder()
                .chatRoomId(chatRoomId)
                .senderId(senderId)
                .senderNickName(senderNickName)
                .senderProfileUrl(senderProfileUrl)
                .content(content)
                .unReadCount(unReadCount)
                .build();
    }
}
