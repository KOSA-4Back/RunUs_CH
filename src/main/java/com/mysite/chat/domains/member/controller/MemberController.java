package com.mysite.chat.domains.member.controller;

import com.mysite.chat.domains.member.domain.Member;
import com.mysite.chat.domains.member.dto.request.ReceiveCreateMemberRequest;
import com.mysite.chat.domains.member.dto.request.ReceiveDeleteMemberRequest;
import com.mysite.chat.domains.member.dto.request.ReceiveMemberUpdateRequest;
import com.mysite.chat.domains.member.dto.request.UpdateMemberProfileRequest;
import com.mysite.chat.domains.member.dto.response.GetMemberChatInfoResponse;
import com.mysite.chat.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mysite.chat.domains.member.service.MemberService;

import java.time.LocalDateTime;
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
@Slf4j
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


    /*@RabbitListener(queues = "member.create.queue")
    public void handleUserCreateMessage(ReceiveCreateMemberRequest message) {
        log.info("handleUserCreateMessage: {}", message);
        memberService.handleUserCreateMessage(message);
    }*/



}
