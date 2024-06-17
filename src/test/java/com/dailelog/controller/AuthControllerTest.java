package com.dailelog.controller;

import com.dailelog.domain.User;
import com.dailelog.repository.UserRepository;
import com.dailelog.request.Login;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    public void test() throws Exception{
        //given
        userRepository.save(User.builder()
                .loginId("userid")
                .password("1234")
                .name("김동혁")
                .build());

        Login login = Login.builder()
                .loginId("userid")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        //excepted
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그인 성공 후 세션 1개")
    public void tes2() throws Exception{
        //given
        User user = userRepository.save(User.builder()
                .loginId("userid")
                .password("1234")
                .name("김동혁")
                .build());

        Login login = Login.builder()
                .loginId("userid")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        //excepted
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        User loggedInUser = userRepository.findByLoginId(user.getLoginId())
                .orElseThrow(RuntimeException::new);

        assertEquals(1L,loggedInUser.getSessions().size());
    }
}