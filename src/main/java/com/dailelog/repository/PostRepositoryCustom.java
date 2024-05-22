package com.dailelog.repository;

import com.dailelog.domain.Post;
import com.dailelog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
