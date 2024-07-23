package com.mysite.chat.domains.user.dto.message;

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
public record ReceiveDeleteMessageFormatter(long userId, LocalDateTime deletedAt) {
}
