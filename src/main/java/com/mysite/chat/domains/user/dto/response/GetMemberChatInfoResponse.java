package com.mysite.chat.domains.user.dto.response;

import com.mysite.chat.domains.user.domain.Member;
import lombok.Builder;

/**
 * packageName    : user.dto.response
 * fileName       : GetAllUserResponse
 * author         : Yeong-Huns
 * date           : 2024-07-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-20        Yeong-Huns       최초 생성
 */
@Builder
public record GetMemberChatInfoResponse(long id, String username, String email) {
    public static GetMemberChatInfoResponse fromUser(Member member) {
        return GetMemberChatInfoResponse.builder()
                .id(member.getUserId())
                .username(member.getNickName())
                .email(member.getEmail())
                .build();
    }
}
