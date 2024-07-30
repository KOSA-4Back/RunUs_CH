package com.mysite.chat.domains.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mysite.chat.domains.user.domain.MongoUser;

/**
 * packageName    : com.mysite.chat.domains.user.dto
 * fileName       : User
 * author         : Yeong-Huns
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        Yeong-Huns       최초 생성
 */
public record User(
        long id,
        String name,
        @JsonProperty("profileUrl") String profileUrl
) {
    public MongoUser toEntity(){
        return MongoUser.builder()
                .id(id)
                .name(name)
                .profileUrl(profileUrl).build();
    }
}
