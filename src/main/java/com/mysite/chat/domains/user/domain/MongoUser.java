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
public class MongoUser {
    @Id
    private long id;
    private String name;
    private String profileUrl;
    private List<String> chatRooms;

    @Builder
    public MongoUser(long id, String name, String profileUrl) {
        this.id = id;
        this.name = name;
        this.profileUrl = profileUrl;
        this.chatRooms = new ArrayList<>();
    }

    public MongoUser addChatRoom(String chatRoom) {
        this.chatRooms.add(chatRoom);
        return this;
    }

    public MongoUser removeChatRoom(String chatRoom) {
        this.chatRooms.remove(chatRoom);
        return this;
    }

    public MongoUser updateProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
        return this;
    }
}
