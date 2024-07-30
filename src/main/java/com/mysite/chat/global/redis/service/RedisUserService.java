package com.mysite.chat.global.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * packageName    : com.fourback.runus.global.security.config
 * fileName       : RedisUserService
 * author         : Yeong-Huns
 * date           : 2024-07-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-26        Yeong-Huns       최초 생성
 */
@RequiredArgsConstructor
@Service
public class RedisUserService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, String> sessionRedisTemplate;


    public String getUserToken(String userId){
        String key = "member:"+userId;
        return (String) redisTemplate.opsForHash().get(key, "Token");
    }

    public String getUserConnectionStatus(String userId){
        String key = "member:"+userId;
        return (String) redisTemplate.opsForHash().get(key, "ConnectionStatus");
    }

    public void saveSessionKey(String sessionId, String userId){ //세션 아이디 저장
        String sessionKey = "session:" + sessionId;
        sessionRedisTemplate.opsForValue().set(sessionKey, String.valueOf(userId));
        sessionRedisTemplate.expire(sessionKey, Duration.ofHours(24)); // 세션 만료시간 설정
    }

    public String getUserIdBySessionKey(String sessionKey){ // 세션 아이디로 유저 아이디 조회
        String key = "session:"+sessionKey;
        return sessionRedisTemplate.opsForValue().get(key);
    }

    public void updateUserConnectionStatus(String userId, boolean connectionStatus){ // 웹소켓 연결상태 업데이트
        redisTemplate.opsForHash().put("member:"+userId, "ConnectionStatus", connectionStatus ? "true" : "false");
    }

    public void deleteSession(String sessionId){ // 세션 아이디 파기
        String key = "session:"+sessionId;
        sessionRedisTemplate.delete(key);
    }

}
