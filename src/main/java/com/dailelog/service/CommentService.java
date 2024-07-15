package com.dailelog.service;

import com.dailelog.domain.Comment;
import com.dailelog.domain.Post;
import com.dailelog.exception.CommentNotFound;
import com.dailelog.exception.InvalidPassword;
import com.dailelog.exception.PostNotFound;
import com.dailelog.repository.comment.CommentRepository;
import com.dailelog.repository.post.PostRepository;
import com.dailelog.request.comment.CommentCreate;
import com.dailelog.request.comment.CommentDelete;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.apache.tomcat.util.http.parser.Cookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void write(Long postId, CommentCreate request) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Comment comment = Comment.builder()
                .author(request.getAuthor())
                .password(encryptedPassword)
                .content(request.getContent())
                .build();

        post.addComment(comment);
    }

    public void delete(Long commentId, CommentDelete request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFound::new);

        String encryptedPassword = comment.getPassword();

        if (!passwordEncoder.matches(request.getPassword(), encryptedPassword)) {
            throw new InvalidPassword();
        }

        commentRepository.delete(comment);
    }
}
