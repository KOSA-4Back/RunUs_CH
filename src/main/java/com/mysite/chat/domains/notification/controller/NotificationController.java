package com.mysite.chat.domains.notification.controller;

import com.mysite.chat.domains.notification.domain.Notification;
import com.mysite.chat.domains.notification.dto.request.CreateNotificationRequest;
import com.mysite.chat.domains.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.mysite.chat.domains.alram
 * fileName       : AlarmController
 * author         : Yeong-Huns
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        Yeong-Huns       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    // 알람 생성
    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody CreateNotificationRequest request) {
        Notification notification = notificationService.createNotification(request);
        return ResponseEntity.ok(notification);
    }

    // 알람 조회
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotification(@PathVariable("id") ObjectId notificationId) {
        Notification notification = notificationService.getNotification(notificationId);
        return ResponseEntity.ok(notification);
    }

    // 특정 유저의 전체 알람 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getAllNotifications(@PathVariable("userId") Long userId) {
        List<Notification> notifications = notificationService.getAllNotifications(userId);
        return ResponseEntity.ok(notifications);

    }

    // 알람 읽음 (업데이트) 처리
    @PutMapping("/read/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable("id") ObjectId notificationId) {
        Notification notification = notificationService.updateNotification(notificationId);
        return ResponseEntity.ok(notification);
    }
}
