package com.mysite.chat.domains.user.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.mysite.chat.global.rabbitMQ.user.domain
 * fileName       : User
 * author         : Yeong-Huns
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        Yeong-Huns       최초 생성
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "users")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserAddress {
    @Id
    private long id;
    private List<String> chatRoomAddress;

    @Builder
    public UserAddress(long id) {
        this.id = id;
        this.chatRoomAddress = new ArrayList<>();
    }

    public UserAddress addChatRoom(String chatRoomAddress) {
        this.chatRoomAddress.add(chatRoomAddress);
        return this;
    }

    public UserAddress removeChatRoom(String chatRoomAddress) {
        this.chatRoomAddress.remove(chatRoomAddress);
        return this;
    }
}
