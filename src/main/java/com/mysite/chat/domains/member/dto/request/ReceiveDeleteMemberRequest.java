package com.mysite.chat.domains.member.dto.request;

import java.time.LocalDateTime;

/**
 * packageName    : com.mysite.chat.domains.user.dto.message
 * fileName       : ReceiveDeleteMessage
 * author         : Yeong-Huns
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        Yeong-Huns       최초 생성
 */
public record ReceiveDeleteMemberRequest(long userId, LocalDateTime deletedAt) {
}
