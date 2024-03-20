package com.fastcampus.shoploadbook.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
}