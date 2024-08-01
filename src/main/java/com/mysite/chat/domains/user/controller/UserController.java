package com.mysite.chat.domains.user.controller;

import com.mysite.chat.domains.user.dto.AddressRequest;
import com.mysite.chat.domains.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.mysite.chat.domains.user.controller
 * fileName       : UserController
 * author         : Yeong-Huns
 * date           : 2024-08-01
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-08-01        Yeong-Huns       최초 생성
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("api/User")
public class UserController {
    private final UserService userService;

    @PostMapping
    public void addChatRoomAddress(@RequestBody AddressRequest request){
        userService.addAddress(request);
    }

    @PutMapping
    public void removeChatRoomAddress(@RequestBody AddressRequest request){
        userService.removeAddress(request);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<String>> getAllChatRoomAddress(@PathVariable long userId){
        return ResponseEntity.ok(userService.getAllAddress(userId));
    }

}
