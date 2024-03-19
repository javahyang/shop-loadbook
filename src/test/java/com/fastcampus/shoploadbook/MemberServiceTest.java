package com.fastcampus.shoploadbook;

import com.fastcampus.shoploadbook.controller.dto.MemberFormRequestDto;
import com.fastcampus.shoploadbook.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@TestPropertySource(properties = "spring.config.name=application")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private Member createMember() {
        MemberFormRequestDto dto = new MemberFormRequestDto(
                "김나라",
                "nara@abc.com",
                "abc1234!",
                "버지니아주 제주시"
        );

        return Member.of(dto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    void saveMember() {
        // given
        Member member = createMember();

        // when
        Member savedMember = memberService.saveMember(member);

        // then
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());
    }

    @Test
    @DisplayName("중복 회원가입하는 경우 예외 반환")
    void test() {
        // given
        Member member1 = createMember();
        Member member2 = createMember();

        // when & then
        memberService.saveMember(member1);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);
        });
        assertEquals("이미 가입된 회원입니다.", exception.getMessage());
    }
}