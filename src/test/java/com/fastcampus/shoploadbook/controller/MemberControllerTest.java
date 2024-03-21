package com.fastcampus.shoploadbook.controller;

import com.fastcampus.shoploadbook.service.MemberService;
import com.fastcampus.shoploadbook.controller.dto.MemberFormDto;
import com.fastcampus.shoploadbook.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberController memberController;

    private static final String BASE_URL = "/members";

    @Test
    @DisplayName("회원가입 view를 로딩한다")
    void viewMemberNew() throws Exception {
        // then
        mockMvc.perform(get(BASE_URL + "/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/memberForm"))
                .andExpect(model().attributeExists("dto"));
    }

    @Test
    @DisplayName("회원가입 완료 후 홈화면으로 이동한다")
    void test() throws Exception {
        // given
        MemberFormDto dto = new MemberFormDto();
        dto.setName("김나라");
        dto.setEmail("nara@abc.com");
        dto.setPassword("abc1234!");
        dto.setAddress("버지니아주 제주시");

        // then
        mockMvc.perform(post(BASE_URL + "/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", dto.getName())
                        .param("email", dto.getEmail())
                        .param("password", dto.getPassword())
                        .param("address", dto.getAddress())
                        .with(csrf())) // csrf() 사용하려면 spring-security-test Dependency 필수
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(memberService, times(1)).saveMember(any(Member.class));
    }

    @Test
    @DisplayName("회원가입시 유효성 검사에 실패한 케이스")
    void memberNew2() throws Exception {
        // given
        MemberFormDto dto = new MemberFormDto();
        dto.setName("김나라");
        dto.setEmail("nara@abc.com");
        dto.setPassword("abc1234!");
        dto.setAddress("");

        // then
        mockMvc.perform(post(BASE_URL + "/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", dto.getName())
                        .param("email", dto.getEmail())
                        .param("password", dto.getPassword())
                        .param("address", dto.getAddress())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dto")) // @ModelAttribute("dto") 설정과 같은 값
                .andExpect(view().name("member/memberForm"));
    }

    @Test
    @DisplayName("중복 회원가입으로 예외 반환하는 케이스")
    void memberNew3() throws Exception {
        // given
        MemberFormDto dto = new MemberFormDto();
        dto.setName("김나라");
        dto.setEmail("nara@abc.com");
        dto.setPassword("abc1234!");
        dto.setAddress("버지니아주 제주시");
        String errorMessage = "이미 가입된 회원입니다.";

        // when
        Mockito.doThrow(new IllegalStateException(errorMessage))
                .when(memberService)
                .saveMember(Mockito.any(Member.class));

        // then
        mockMvc.perform(post(BASE_URL + "/new")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", dto.getName())
                        .param("email", dto.getEmail())
                        .param("password", dto.getPassword())
                        .param("address", dto.getAddress())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("member/memberForm"))
                .andExpect(model().attribute("errorMessage", errorMessage));
    }
}