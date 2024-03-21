package com.fastcampus.shoploadbook.service;

import com.fastcampus.shoploadbook.entity.Member;
import com.fastcampus.shoploadbook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMemberEmail(member.getEmail());
        return memberRepository.save(member);
    }

    private void validateDuplicateMemberEmail(String email) {
        Member findMember = memberRepository.findByEmail(email);
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
