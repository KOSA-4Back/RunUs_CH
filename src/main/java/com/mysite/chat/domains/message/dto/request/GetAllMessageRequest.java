package com.mysite.chat.domains.message.dto.request;

import org.bson.types.ObjectId;

/**
 * packageName    : com.mysite.chat.domains.message.dto.request
 * fileName       : GetAllMessageRequest
 * author         : Yeong-Huns
 * date           : 2024-07-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-25        Yeong-Huns       최초 생성
 */
public record GetAllMessageRequest(
        String chatRoomId,
        long userId
) {
}
