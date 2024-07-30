package com.mysite.chat.domains.user.dto;

import com.mysite.chat.domains.user.domain.MongoUser;

/**
 * packageName    : com.mysite.chat.global.rabbitMQ.user.domain
 * fileName       : UserEvent
 * author         : Yeong-Huns
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        Yeong-Huns       최초 생성
 */
public record UserEvent(String type, User user) {
}
