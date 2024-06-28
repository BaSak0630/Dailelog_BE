package com.dailelog.controller;

import com.dailelog.request.comment.CommentCreate;
import com.dailelog.request.comment.CommentDelete;
import com.dailelog.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    private void write(@PathVariable Long postId, @RequestBody @Valid CommentCreate request){
        commentService.write(postId, request);
    }

    @PostMapping("/comments/{commentId}/delete")//delete requestBody 를 못넣는다
    private void delete(@PathVariable Long commentId, @RequestBody @Valid CommentDelete request) {
        commentService.delete(commentId, request);
    }
}
