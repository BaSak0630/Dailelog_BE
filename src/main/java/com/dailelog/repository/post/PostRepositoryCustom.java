package com.dailelog.repository.post;

import com.dailelog.domain.Post;
import com.dailelog.request.post.PostSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostRepositoryCustom {

    Page<Post> getList(PostSearch postSearch);
}
