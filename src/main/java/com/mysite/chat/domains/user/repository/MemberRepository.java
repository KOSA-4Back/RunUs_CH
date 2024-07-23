package com.mysite.chat.domains.user.repository;

import com.mysite.chat.domains.user.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

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
}
