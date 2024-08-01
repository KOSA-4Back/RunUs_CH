package com.mysite.chat.domains.user.Repository;

import com.mysite.chat.domains.user.domain.UserAddress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.mysite.chat.global.rabbitMQ.user.Repository
 * fileName       : UserRepository
 * author         : Yeong-Huns
 * date           : 2024-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-31        Yeong-Huns       최초 생성
 */
@Repository
public interface UserRepository extends MongoRepository<UserAddress, Long> {
}
