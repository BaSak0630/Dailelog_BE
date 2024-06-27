package com.dailelog.repository.post;

import com.dailelog.domain.Post;
import com.dailelog.request.post.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
