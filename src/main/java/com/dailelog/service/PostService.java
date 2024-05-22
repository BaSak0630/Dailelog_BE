package com.dailelog.service;

import com.dailelog.domain.Post;
import com.dailelog.domain.PostEditor;
import com.dailelog.exception.PostNotFound;
import com.dailelog.repository.PostRepository;
import com.dailelog.request.PostCreate;
import com.dailelog.request.PostEdit;
import com.dailelog.request.PostSearch;
import com.dailelog.response.PostResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate){
        //postCreate -> Entity
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

       postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        /*
        * PostController -> WebPostService -> Repository
        *                   PostService
        */
    }

    //글이 너무 많은 경우 -> 비용이 너무 많이 든다.
    //글리 -> 100.000.000-> DB글 모두 조회하는 경우 -> DB가 뻗을 수 있다.
    //DB-> 애플리케이션 서버로 전달하는 시간, 트래픽비용 등이 많이 들 수 있다.
    public List<PostResponse> getList(PostSearch postSearch) {
        //web -> page 1 -> 0 변환해줌 yml
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);

      /*  if(postEdit.getTitle() != null) {
            editorBuilder.title(postEdit.getTitle());
        }

        if(postEdit.getContent() != null) {
            editorBuilder.content(postEdit.getContent());
        }

        post.edit( editorBuilder.build());*/
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }
}
