package com.mysite.chat.domains.chatRoom.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * packageName    : com.mysite.chat.domains.chatRoom.domain
 * fileName       : Participant
 * author         : Yeong-Huns
 * date           : 2024-07-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-25        Yeong-Huns       최초 생성
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Participant {
    private long userId;
    private LocalDateTime joinedAt;

    // 생성자
    public Participant(long userId) {
        this.userId = userId;
        this.joinedAt = LocalDateTime.now();
    }

    // 정적 팩토리 메서드
    public static Participant from(long userId) {
        return new Participant(userId);
    }
}
