package com.mysite.chat.global.rabbitMQ.listener;

import com.mysite.chat.domains.user.domain.Member;
import com.mysite.chat.domains.user.dto.request.ReceiveDeleteMemberRequest;
import com.mysite.chat.domains.user.dto.request.UpdateMemberProfileRequest;
import com.mysite.chat.domains.user.dto.request.ReceiveMemberUpdateRequest;
import com.mysite.chat.domains.user.dto.request.ReceiveCreateMemberRequest;
import com.mysite.chat.domains.user.repository.MemberRepository;
import com.mysite.chat.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
    public void handleUserCreateMessage(ReceiveCreateMemberRequest message) {
      log.info("handleUserCreateMessage: {}", message);
      memberRepository.save(message.toEntity());
    }

    @RabbitListener(queues = "chat.member.update.queue")
    public void handleMemberUpdateMessage(ReceiveMemberUpdateRequest message) {
        log.info("handleUserUpdateMessage: {}", message);
        Member member = memberRepository.findById(message.userId())
                .orElseThrow(()->new NotFoundException("해당 Member 의 Update 에 실패하였습니다. "))
                .updateMemberInfo(message);
        memberRepository.save(member);
    }

    @RabbitListener(queues = "chat.member.update.role.queue")
    public void handleMemberUpdateMessage(long id) {
        log.info("handleUserUpdateRoleMessage: {}", id);
        Member member = memberRepository.findById(id)
                .orElseThrow(()->new NotFoundException("해당 Member 의 ADMIN 변경에 실패하였습니다."))
                .updateRoleToAdmin();
        memberRepository.save(member);
    }

    @RabbitListener(queues = "chat.member.update.profile.queue")
    public void handleMemberUpdateProfile(UpdateMemberProfileRequest message){
        log.info("handleUserUpdateProfile");
        Member member = memberRepository.findById(message.userId())
                .orElseThrow(()->new NotFoundException("해당 멤버의 프로필 업데이트에 실패하였습니다."))
                .updateProfileUrl(message.profileUrl());
        memberRepository.save(member);
    }


    @RabbitListener(queues = "chat.member.delete.queue")
    public void handleUserDeleteMessage(ReceiveDeleteMemberRequest message) {
        log.info("deleteUserById: {}", message);
        Member member = memberRepository.findById(message.userId())
                .orElseThrow(()->new NotFoundException("회원 삭제에 실패 하였습니다."))
                .deleteMember(message.deletedAt());
        memberRepository.save(member);
    }

    @RabbitListener(queues = "chat.member.delete.all.queue")
    public void handleUserDeleteAllMessage(LocalDateTime deletionTime) {
        log.info("deleteAllUserMessage: {}", deletionTime);
        memberRepository.findAll()
                .forEach(member -> memberRepository.save(member.deleteMember(deletionTime)));
    }
}
