package com.mysite.chat.domains.user.dto;

import com.mysite.chat.domains.user.domain.UserAddress;

/**
 * packageName    : com.mysite.chat.domains.user.dto
 * fileName       : AddAddressRequest
 * author         : Yeong-Huns
 * date           : 2024-08-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-01        Yeong-Huns       최초 생성
 */
public record AddressRequest(long userId, String address) {
    public UserAddress toEntity(){
        return UserAddress.builder()
                .id(userId)
                .build();
    }
}
