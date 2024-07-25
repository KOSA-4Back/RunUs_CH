package com.mysite.chat.domains.member.service;

import com.mysite.chat.domains.member.dto.request.ReceiveCreateMemberRequest;
import com.mysite.chat.domains.member.dto.request.ReceiveDeleteMemberRequest;
import com.mysite.chat.domains.member.dto.request.ReceiveMemberUpdateRequest;
import com.mysite.chat.domains.member.dto.request.UpdateMemberProfileRequest;
import com.mysite.chat.domains.member.dto.response.GetMemberChatInfoResponse;
import com.mysite.chat.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mysite.chat.domains.member.domain.Member;
import com.mysite.chat.domains.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : user.mysite.chat
 * fileName       : UserService
 * author         : Yeong-Huns
 * date           : 2024-07-20
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-20        Yeong-Huns       최초 생성
 */
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<GetMemberChatInfoResponse> findAll(){
        return memberRepository.findAllActiveMembers()
                .stream()
                .map(GetMemberChatInfoResponse::from)
                .toList();
    }

    public GetMemberChatInfoResponse findById(Long id){
        Member member = memberRepository.findActiveMemberById(id).orElseThrow();
        return GetMemberChatInfoResponse.from(member);
    }

    public void handleUserCreateMessage(ReceiveCreateMemberRequest message) {
        memberRepository.save(message.toEntity());
    }

    public void handleMemberUpdateMessage(ReceiveMemberUpdateRequest message) {
        Member member = memberRepository.findById(message.userId())
                .orElseThrow(()->new NotFoundException("해당 Member 의 Update 에 실패하였습니다. "))
                .updateMemberInfo(message);
        memberRepository.save(member);
    }

    public void handleMemberUpdateRole(long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(()->new NotFoundException("해당 Member 의 ADMIN 변경에 실패하였습니다. "))
                .updateRoleToAdmin();
        memberRepository.save(member);
    }

    public void handleMemberUpdateProfile(UpdateMemberProfileRequest message){
        Member member = memberRepository.findById(message.userId())
                .orElseThrow(()->new NotFoundException("해당 멤버의 프로필 업데이트에 실패하였습니다."))
                .updateProfileUrl(message.profileUrl());
        memberRepository.save(member);
    }

    public void handleUserDeleteMessage(ReceiveDeleteMemberRequest message) {
        Member member = memberRepository.findById(message.userId())
                .orElseThrow(()->new NotFoundException("회원 삭제에 실패 하였습니다."))
                .deleteMember(message.deletedAt());
        memberRepository.save(member);
    }

    public void handleUserDeleteAllMessage(LocalDateTime deletionTime) {
        memberRepository.findAll()
                .forEach(member -> memberRepository.save(member.deleteMember(deletionTime)));
    }



}
