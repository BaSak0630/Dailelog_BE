package com.dailelog.response;

import com.dailelog.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/*
* 서비스 정책의 맞는 클래스
*/
@Getter

public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime regDate;

    //생성자 오버로딩
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.regDate = post.getRegDate();
    }

    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
        this.regDate = LocalDateTime.now();
    }
}
