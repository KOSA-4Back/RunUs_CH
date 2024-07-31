package com.mysite.chat.domains.notification.repository;

import com.mysite.chat.domains.notification.domain.Notification;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.mysite.chat.domains.alram.repository
 * fileName       : AlarmRepository
 * author         : Yeong-Huns
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        Yeong-Huns       최초 생성
 */
@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
}
