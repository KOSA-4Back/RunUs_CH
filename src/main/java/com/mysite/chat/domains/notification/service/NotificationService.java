package com.mysite.chat.domains.notification.service;

import com.mysite.chat.domains.notification.domain.Notification;
import com.mysite.chat.domains.notification.dto.request.CreateNotificationRequest;
import com.mysite.chat.domains.notification.repository.NotificationRepository;
import com.mysite.chat.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName    : com.mysite.chat.domains.notification.service
 * fileName       : NotificationService
 * author         : Yeong-Huns
 * date           : 2024-07-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-25        Yeong-Huns       최초 생성
 */
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    // 알람 생성
    public Notification createNotification(CreateNotificationRequest request) {
        return notificationRepository.save(request.toEntity());
    }


    // 알람 조회
    public Notification getNotification(ObjectId notificationId) {
        return notificationRepository.findById(notificationId).orElseThrow(()->new NotFoundException("해당 유저의 알람을 조회하는데 실패하였습니다."));
    }

    // 특정 유저의 전체 알람 조회
    public List<Notification> getAllNotifications(Long userId) {
        return notificationRepository.findAll();
    }

    // 알람 읽음 (업데이트) 처리
    public Notification updateNotification(ObjectId notificationId) {
        return notificationRepository.save(
                notificationRepository.findById(notificationId)
                        .orElseThrow(() -> new NotFoundException("해당 유저의 알람은 존재하지않습니다."))
                        .updateIsReadTrue()
        );
    }

    // 알람 삭제?
}
