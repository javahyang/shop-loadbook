package com.fastcampus.shoploadbook.entity;

import com.fastcampus.shoploadbook.constant.Role;
import com.fastcampus.shoploadbook.controller.dto.MemberFormRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member of(
        MemberFormRequestDto dto,
        PasswordEncoder passwordEncoder
    ) {
        return Member.builder()
                .name(dto.name())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .address(dto.address())
                .role(Role.USER)
                .build();
    }
}
