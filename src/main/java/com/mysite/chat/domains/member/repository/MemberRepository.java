package com.mysite.chat.domains.member.repository;

import com.mysite.chat.domains.member.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : user.repository
 * fileName       : UserRepository
 * author         : Yeong-Huns
 * date           : 2024-07-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-19        Yeong-Huns       최초 생성
 */
public interface MemberRepository extends MongoRepository<Member, Long> {
    @Query("{ 'deletedAt': null }")
    List<Member> findAllActiveMembers();

    @Query("{ 'userId': ?0, 'deletedAt': null }") // ?0 : 첫번째 파라미터(userId)
    Optional<Member> findActiveMemberById(long id);
}
