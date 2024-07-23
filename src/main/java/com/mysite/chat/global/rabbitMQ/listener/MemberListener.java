package com.mysite.chat.global.rabbitMQ.listener;

import com.mysite.chat.domains.user.domain.Member;
import com.mysite.chat.domains.user.dto.request.UpdateMemberProfileRequest;
import com.mysite.chat.domains.user.dto.response.ReceiveMemberUpdateFormatter;
import com.mysite.chat.domains.user.dto.response.ReceiveMessageFormatter;
import com.mysite.chat.domains.user.repository.MemberRepository;
import com.mysite.chat.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * packageName    : user.controller
 * fileName       : UserListener
 * author         : Yeong-Huns
 * date           : 2024-07-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-19        Yeong-Huns       최초 생성
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MemberListener {
    private final MemberRepository memberRepository;

    @RabbitListener(queues = "member.create.queue")
    public void handleUserCreateMessage(ReceiveMessageFormatter message) {
      log.info("handleUserCreateMessage: {}", message);
      memberRepository.save(message.toEntity());
    }

    @RabbitListener(queues = "member.update.queue")
    public void handleMemberUpdateMessage(ReceiveMemberUpdateFormatter message) {
        log.info("handleUserUpdateMessage: {}", message);
        Member member = memberRepository.findById(message.id())
                .orElseThrow(()->new NotFoundException("해당 Member 의 Update 에 실패하였습니다. "))
                .updateMemberInfo(message);
        memberRepository.save(member);
    }

    @RabbitListener(queues = "member.update.queue")
    public void handleMemberUpdateProfile(UpdateMemberProfileRequest message){
        log.info("handleUserUpdateProfile");
        Member member = memberRepository.findById(message.id())
                .orElseThrow(()->new NotFoundException("해당 멤버의 프로필 업데이트에 실패하였습니다."))
                .updateProfileUrl(message.profileUrl());
        memberRepository.save(member);
    }


    @RabbitListener(queues = "member.delete.queue")
    public void handleUserDeleteMessage(long id) {
        log.info("deleteUserById: {}", id);
        memberRepository.deleteById(id);
    }

    @RabbitListener(queues = "member.delete.all.queue")
    public void handleUserDeleteAllMessage(String message) {
        log.info("deleteAllUserMessage: {}", message);
        memberRepository.deleteAll();
    }
}
