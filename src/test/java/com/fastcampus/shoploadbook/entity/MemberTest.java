package com.fastcampus.shoploadbook.entity;

import com.fastcampus.shoploadbook.constant.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class MemberTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Member 빌드 테스트")
    void buildMember() {
        // given
        Member member = Member.builder()
                .name("김나라")
                .email("nara@abc.com")
                .password(passwordEncoder.encode("abc1234!"))
                .role(Role.USER)
                .build();

        // then
        System.out.println(member.getPassword());
        Assertions.assertEquals(Role.USER, member.getRole());
    }
}