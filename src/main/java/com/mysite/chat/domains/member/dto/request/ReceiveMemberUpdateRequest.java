<<<<<<<< HEAD:src/main/java/com/mysite/chat/domains/member/dto/response/ReceiveMemberUpdateFormatter.java
package com.mysite.chat.domains.member.dto.response;
========
package com.mysite.chat.domains.user.dto.request;
>>>>>>>> 9d9f5b95feb9e939a5f019c278b1f9393062259b:src/main/java/com/mysite/chat/domains/member/dto/request/ReceiveMemberUpdateRequest.java

import java.time.LocalDate;

/**
 * packageName    : com.fourback.runus.domains.member.dto.response
 * fileName       : ReceiveMemberUpdateFormatter
 * author         : Yeong-Huns
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        Yeong-Huns       최초 생성
 */
public record ReceiveMemberUpdateRequest(
        long userId,
        String nickName,
        LocalDate birth,
        int height,
        int weight
) {
}
