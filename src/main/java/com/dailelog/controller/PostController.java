package com.dailelog.controller;

import com.dailelog.domain.Post;
import com.dailelog.exception.InvalidRequest;
import com.dailelog.repository.PostRepository;
import com.dailelog.request.PostCreate;
import com.dailelog.request.PostEdit;
import com.dailelog.request.PostSearch;
import com.dailelog.response.PostResponse;
import com.dailelog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//SSR -> jsp,thymeleaf,mustache, freemarker
// -> html rendering
//SPA -> vue
// -> javascript + <-> API (JSON)
// vue, nuxt
// react, next

// Case1. 저장한 데이터 Entity -> response로 응답하기
//        //Case2. 저장한 데이터의 primary_id -> response로 응답하기
//        //      client에서는 수신한 id를 글 조회 API를 통해서 데이터를 수신받음
//        //Case3. 응답 필요없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 잘 관리함
//        // Bad Case: 서버에서 -> 반드시 이렇게 할껍니다. !fix
//        //      -> 서버에서 차라리 유연하게 대응하는게 좋습니다. -> 코드를 잘 짜야함
//        //      -> 한번에  일괄적으로 잘 처리되는 케이스가 없습니다. -> 잘관리하는 형태가 중요함

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    //글 등록
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request){
        request.validate();

        postService.write(request);
        //return postService.write(request);
    }

    @GetMapping("/posts")
    public List<PostResponse> getAll(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }


    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id) {
        //Request 클래스 요청과 벨리데이션 용
        //Response 클래스 정책등을 반영한 응답 전용 클래스

        return postService.get(id);
        //응답 클래스를 분리하세요(서비스 정책의 맞는)
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable(name = "postId") Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable(name = "postId") Long id) {
        postService.delete(id);
    }
}

