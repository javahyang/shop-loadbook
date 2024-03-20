package com.fastcampus.shoploadbook.controller;

import com.fastcampus.shoploadbook.MemberService;
import com.fastcampus.shoploadbook.controller.dto.MemberFormDto;
import com.fastcampus.shoploadbook.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model) {
        model.addAttribute("dto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String memberForm(MemberFormDto dto) {
        Member member = Member.of(dto, passwordEncoder);
        memberService.saveMember(member);
        return "redirect:/";
    }
}
