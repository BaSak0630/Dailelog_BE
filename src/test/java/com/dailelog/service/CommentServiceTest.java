package com.dailelog.service;

import com.dailelog.domain.Comment;
import com.dailelog.domain.Post;
import com.dailelog.domain.User;
import com.dailelog.repository.UserRepository;
import com.dailelog.repository.comment.CommentRepository;
import com.dailelog.repository.post.PostRepository;
import com.dailelog.request.comment.CommentCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentServiceTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService  commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("댓글 작성")
    public void write() throws Exception{
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

        //when
        commentService.write(user.getId(), commentCreate);

        Comment comment = commentRepository.findAll().get(0);
        //then
        assertEquals(commentRepository.count(),1L);
        assertEquals(commentCreate.getAuthor(), comment.getAuthor());
        assertTrue(passwordEncoder.matches(commentCreate.getPassword(), comment.getPassword()));
        assertEquals(commentCreate.getContent(), comment.getContent());
    }
}
