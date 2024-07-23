package com.mysite.chat.domains.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.mysite.chat.domains.user.domain.Member;
import com.mysite.chat.domains.user.dto.response.GetMemberChatInfoResponse;
import com.mysite.chat.domains.user.repository.MemberRepository;

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

    public boolean idExist(Long id){
        return memberRepository.existsById(id);
    }
}
