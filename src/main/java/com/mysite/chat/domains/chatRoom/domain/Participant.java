package com.mysite.chat.domains.chatRoom.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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

    // Participant 배열에서 제거(remove)를 위한 equals 오버라이드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return userId == that.userId;
    }

    // Participant 배열에서 제거를 위한 hashcode 오버라이드
    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
