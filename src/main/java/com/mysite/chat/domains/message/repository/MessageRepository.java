package com.mysite.chat.domains.message.repository;

import com.mysite.chat.domains.message.domain.Message;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : com.mysite.chat.domains.message.repository
 * fileName       : MessageRepository
 * author         : Yeong-Huns
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        Yeong-Huns       최초 생성
 */
@Repository
public interface MessageRepository extends MongoRepository<Message, ObjectId> {
    // 쿼리 동적 생성
    // ChatRoomId 와 CreatedAt이 joinedAt 보다 이후의 모든 Message 를 찾아서 orderBy 해라
    List<Message> findAllByChatRoomIdAndCreatedAtAfterOrderByCreatedAtDesc(ObjectId chatRoomId, LocalDateTime joinedAt);
}
