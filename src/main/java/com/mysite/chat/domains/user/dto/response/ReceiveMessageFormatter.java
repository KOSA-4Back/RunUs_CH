package com.mysite.chat.domains.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mysite.chat.domains.user.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * packageName    : com.mysite.chat.user.dto.response
 * fileName       : ReceiveMessageFormatter
 * author         : Yeong-Huns
 * date           : 2024-07-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-21        Yeong-Huns       최초 생성
 */
public record ReceiveMessageFormatter(
        long userId,
        String email,
        String nickName,
        LocalDate birth,
        int height,
        int weight,
        @JsonProperty("createdAt") LocalDateTime createdAt,
        @JsonProperty("updatedAt") LocalDateTime updatedAt,
        @JsonProperty("deletedAt") LocalDateTime deletedAt) {
    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .nickName(nickName)
                .email(email)
                .birth(birth)
                .height(height)
                .weight(weight)
                .createdAt (createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
