package com.fastcampus.shoploadbook.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class ThymeleafExControllerTest {

    @Autowired
    MockMvc mockMvc;

    private static final String BASE_URL = "/thymeleaf";

    @Test
    @DisplayName("root 테스트")
    void thymeleafExRootTest() throws Exception {
        // then
        this.mockMvc.perform(get(BASE_URL + "/"))
                .andExpect(status().isOk())
                .andExpect(view().name("thymeleafEx"));
    }
}