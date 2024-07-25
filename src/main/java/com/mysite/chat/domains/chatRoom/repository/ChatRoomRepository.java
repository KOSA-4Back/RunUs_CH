package com.mysite.chat.domains.chatRoom.repository;

import com.mysite.chat.domains.chatRoom.domain.ChatRoom;
import com.mysite.chat.domains.message.dto.response.GetParticipantsInfoResponse;
import com.mysite.chat.global.error.exception.NotFoundException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * packageName    : com.mysite.chat.domains.chatRoom.repository
 * fileName       : ChatRoomRepository
 * author         : Yeong-Huns
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        Yeong-Huns       최초 생성
 */
@Repository
public interface ChatRoomRepository extends MongoRepository <ChatRoom, ObjectId>{
    // 동적 쿼리 생성
    List<ChatRoom> findAllByCreatedBy(long userId);
    // user 가 생성한 채팅방을 최신순(업데이트 순)으로 보여줌
    List<ChatRoom> findAllByCreatedByOrderByUpdatedAtDesc(long createdBy);

    //Mongo Aggregate -> 자바 stream 과 유사
    default GetParticipantsInfoResponse findParticipantJoinedAt(MongoTemplate mongoTemplate, ObjectId chatRoomId, long userId) {
        Aggregation aggregation = newAggregation( // Mongo DB aggregation 사용
                match(where("_id").is(chatRoomId)), // 스트림의 .filter(chatRoom -> chatRoom._id == chatRoomId)
                unwind("participants"), // 내부의 배열을 펼쳐서 flat 으로 바꿔줌 .flatMap()
                match(where("participants.userId").is(userId)),  // .filter(participants -> participants.userId == userId)
                group()
                        .first("participants.joinedAt").as("joinedAt")
                        .addToSet("participants.userId").as("allParticipants"),
                project()
                        .and("participants.joinedAt").as("joinedAt") // .map(participants.joinedAt -> joinedAt)
                        .and(ArrayOperators.Size.lengthOfArray("allParticipants")).as("unReadCount")
        );
        AggregationResults<Document> results = mongoTemplate.aggregate(aggregation, "chatRooms", Document.class);
        // 위에서 생성한 aggregation (파이프라인과 유사) 로 chatRooms 라는 Document 를 거른 결과

        Optional<Document> participant = Optional.ofNullable(results.getUniqueMappedResult());

        Document result = participant.orElseThrow(() -> new NotFoundException("해당 멤버의 채팅방 입장 정보를 조회하는데 실패하였습니다."));
        LocalDateTime joinedAt = result.getDate("joinedAt").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        long unReadCount = result.getLong("unReadCount");
        return GetParticipantsInfoResponse.of(joinedAt, unReadCount);
    }

}
