package com.mysite.chat.domains.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mysite.chat.domains.user.dto.response.GetMemberChatInfoResponse;
import com.mysite.chat.domains.user.service.MemberService;

import java.util.List;

/**
 * packageName    : user.controller
 * fileName       : UserController
 * author         : Yeong-Huns
 * date           : 2024-07-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-20        Yeong-Huns       최초 생성
 */
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/findAll")
    public ResponseEntity<List<GetMemberChatInfoResponse>> getUsers(){
        return ResponseEntity.ok().body(memberService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<GetMemberChatInfoResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(memberService.findById(id));
    }


}
