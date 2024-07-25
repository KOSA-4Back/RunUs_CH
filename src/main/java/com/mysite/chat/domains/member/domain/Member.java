package com.mysite.chat.domains.member.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.mysite.chat.domains.member.dto.request.ReceiveMemberUpdateRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * packageName    : user.domain
 * fileName       : User
 * author         : Yeong-Huns
 * date           : 2024-07-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-19        Yeong-Huns       최초 생성
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "members")
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Member {
    @Id
    private long userId;
    private String email;
    private String nickName;
    private LocalDate birth;
    private String role;
    private String profileUrl;
    private int height;
    private int weight;
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    @Builder
    public Member(long userId, String email, String nickName, LocalDate birth, String role, String profileUrl, int height, int weight, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.userId = userId;
        this.email = email;
        this.nickName = nickName;
        this.birth = birth;
        this.role = role;
        this.profileUrl = profileUrl;
        this.height = height;
        this.weight = weight;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
    public Member deleteMember(LocalDateTime deletedAt){
        this.deletedAt = deletedAt;
        return this;
    }
    public Member updateMemberInfo(ReceiveMemberUpdateRequest message){
        this.nickName = message.nickName();
        this.birth = message.birth();
        this.height = message.height();
        this.weight = message.weight();
        return this;
    }
    public Member updateProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
        return this;
    }
    public Member updateRoleToAdmin(){
        this.role = "ADMIN";
        return this;
    }
}
