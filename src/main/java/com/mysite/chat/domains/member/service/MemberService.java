package com.mysite.chat.domains.member.service;

import com.mysite.chat.domains.member.dto.request.UpdateMemberProfileRequest;
import com.mysite.chat.domains.member.dto.response.ReceiveMemberUpdateFormatter;
import com.mysite.chat.domains.member.dto.response.ReceiveMessageFormatter;
import com.mysite.chat.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mysite.chat.domains.member.domain.Member;
import com.mysite.chat.domains.member.dto.response.GetMemberChatInfoResponse;
import com.mysite.chat.domains.member.repository.MemberRepository;

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
        return memberRepository.findAll()
                .stream()
                .map(GetMemberChatInfoResponse::fromUser)
                .toList();
    }

    public GetMemberChatInfoResponse findById(Long id){
        Member member = memberRepository.findById(id).orElseThrow();
        return GetMemberChatInfoResponse.fromUser(member);
    }

    public void handleUserCreateMessage(ReceiveMessageFormatter message) {
        memberRepository.save(message.toEntity());
    }

    public void handleMemberUpdateMessage(ReceiveMemberUpdateFormatter message) {
        Member member = memberRepository.findById(message.id())
                .orElseThrow(()->new NotFoundException("해당 Member 의 Update 에 실패하였습니다. "))
                .updateMemberInfo(message);
        memberRepository.save(member);
    }

    public void handleMemberUpdateProfile(UpdateMemberProfileRequest message){
        Member member = memberRepository.findById(message.id())
                .orElseThrow(()->new NotFoundException("해당 멤버의 프로필 업데이트에 실패하였습니다."))
                .updateProfileUrl(message.profileUrl());
        memberRepository.save(member);
    }

    public void handleUserDeleteMessage(long id) {
        memberRepository.deleteById(id);
    }

    public void handleUserDeleteAllMessage(String message) {
        memberRepository.deleteAll();
    }



}
