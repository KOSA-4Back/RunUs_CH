package com.mysite.chat.domains.message.dto.response;

import java.time.LocalDateTime;

/**
 * packageName    : com.mysite.chat.domains.message.dto.response
 * fileName       : GetParticipantsInfoResponse
 * author         : Yeong-Huns
 * date           : 2024-07-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-25        Yeong-Huns       최초 생성
 */
public record GetParticipantsInfoResponse(
        LocalDateTime joinedAt,
        long unReadCount
) {
    public static GetParticipantsInfoResponse of(LocalDateTime joinedAt, long unReadCount) {
        return new GetParticipantsInfoResponse(joinedAt, unReadCount);
    }
}
