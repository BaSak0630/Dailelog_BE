package com.dailelog.controller;

import com.dailelog.annotation.DailelogMockUser;
import com.dailelog.config.AppConfig;
import com.dailelog.domain.Comment;
import com.dailelog.domain.Post;
import com.dailelog.domain.User;
import com.dailelog.repository.comment.CommentRepository;
import com.dailelog.repository.post.PostRepository;
import com.dailelog.repository.UserRepository;
import com.dailelog.request.comment.CommentCreate;
import com.dailelog.request.post.PostCreate;
import com.dailelog.request.post.PostEdit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class CommentControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
//각메서드 실행 전에 실행
    void clean() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    @DailelogMockUser
    @DisplayName("댓들 작성 ")
    void test() throws Exception {
        //given
        User user = User.builder()
                .name("김동혁")
                .account("daile")
                .password("1234")
                .email("daile@gmail.com")
                .build();
        userRepository.save(user);

        Post post = Post.builder()
                .title("제목")
                .content("내용입니다.")
                .user(user)
                .build();
        postRepository.save(post);

        CommentCreate commentCreate = CommentCreate.builder()
                .author("동순이")
                .password("1234")
                .content("댓글입니다.")
                .build();

        String json = objectMapper.writeValueAsString(commentCreate);


        //when
        mockMvc.perform(post("/posts/{postId}/comments", post.getId())
                .contentType(APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(1L, commentRepository.count());
        Comment savedComment = commentRepository.findAll().get(0);
        assertEquals("동순이",savedComment.getAuthor());
        assertNotEquals("1234",savedComment.getPassword());
        assertTrue(passwordEncoder.matches("1234",savedComment.getPassword()));
        assertEquals("댓글입니다.",savedComment.getContent());
    }
}