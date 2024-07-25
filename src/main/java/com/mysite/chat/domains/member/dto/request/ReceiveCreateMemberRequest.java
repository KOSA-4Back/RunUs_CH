
package com.mysite.chat.domains.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public record ReceiveCreateMemberRequest(
        long userId,
        String email,
        String nickName,
        LocalDate birth,
        String role,
        @JsonProperty("profileUrl") String profileUrl,
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
                .role(role)
                .profileUrl(profileUrl)
                .height(height)
                .weight(weight)
                .createdAt (createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }
}
