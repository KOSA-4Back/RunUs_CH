package com.mysite.chat.domains.message.dto.response;

import lombok.Builder;

/**
 * packageName    : com.mysite.chat.domains.message.dto.response
 * fileName       : MessageFormat
 * author         : Yeong-Huns
 * date           : 2024-08-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-01        Yeong-Huns       최초 생성
 */
@Builder
public record MessageFormat (
        String chatRoomId,
        long senderId,
        String senderNickName,
        String senderProfileUrl,
        String content
){

}
