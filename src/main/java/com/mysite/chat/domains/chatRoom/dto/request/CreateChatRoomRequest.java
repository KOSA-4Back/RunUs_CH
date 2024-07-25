package com.mysite.chat.domains.chatRoom.dto.request;

import com.mysite.chat.domains.chatRoom.domain.Participant;
import lombok.Builder;

/**
 * packageName    : com.mysite.chat.domains.chatRoom.dto.request
 * fileName       : CreateChatRoomRequest
 * author         : Yeong-Huns
 * date           : 2024-07-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-25        Yeong-Huns       최초 생성
 */
@Builder
public record CreateChatRoomRequest(String title, long createdBy) {
}
