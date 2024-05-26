package com.dailelog.service;

import com.dailelog.domain.Post;
import com.dailelog.exception.PostNotFound;
import com.dailelog.repository.PostRepository;
import com.dailelog.request.PostCreate;
import com.dailelog.request.PostEdit;
import com.dailelog.request.PostSearch;
import com.dailelog.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostServiceTest {
   @Autowired
   private PostService postService;

   @Autowired
   private PostRepository postRepository;

   @BeforeEach
   void clean() {
      postRepository.deleteAll();
   }

   @Test
   @DisplayName("글작성 ")
   public void test1() throws Exception{

      //given
      PostCreate postCreate = PostCreate.builder()
              .title("제목입니다.")
              .content("내용입니다.")
              .build();

      //when
      postService.write(postCreate);

      //then
      assertEquals(1L, postRepository.count());
      Post post = postRepository.findAll().get(0);
      assertEquals("제목입니다.", post.getTitle());
      assertEquals("내용입니다.", post.getContent());
   }

   @Test
   @DisplayName("글 1개 조회")
   public void test2() throws Exception{
       //given
      Post requestPost = Post.builder()
              .title("foo")
              .content("bar")
              .build();
      postRepository.save(requestPost);

      //클라이언트 요구사항
      //    json응답에서 title값 길이를 최대 10글자로 해주세요.원래는 클라이언트에서 처리하는게 맞음


      //when
      PostResponse response = postService.get(requestPost.getId());
      
       //then
      assertNotNull(response);
      assertEquals("foo", response.getTitle());
      assertEquals("bar", response.getContent());
   }

   @Test
   @DisplayName("글 1페이지 조회")
   public void test3() throws Exception{
      //given
      List<Post> requestPosts = IntStream.range(1, 31)
              .mapToObj(i -> Post.builder()
                      .title("daile title " + i)
                      .content("daile content " + i)
                      .build())
              .collect(Collectors.toList());

      postRepository.saveAll(requestPosts);

      PostSearch postSearch = PostSearch.builder()
              .page(1)
              .size(10)
              .build();


      //when
      List<PostResponse> posts = postService.getList(postSearch);

      //then
      assertEquals(10, posts.size());
      assertEquals("daile title 30", posts.get(0).getTitle());
      assertEquals("daile content 30", posts.get(0).getContent());
   }

   @Test
   @DisplayName("글제목 수정")
   public void test4() throws Exception{
      //given
      Post post = Post.builder()
              .title("daile title")
              .content("daile content")
              .build();

      postRepository.save(post);

      PostEdit postEdit = PostEdit.builder()
              .title("변경된 제목")
              .build();


      //when
      postService.edit(post.getId(), postEdit);

      //then
      Post changedPost = postRepository.findById(post.getId())
              .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));
      assertEquals("변경된 제목",changedPost.getTitle());
      assertEquals("daile content",changedPost.getContent());
   }

   @Test
   @DisplayName("글제목 수정")
   public void test5() throws Exception{
      //given
      Post post = Post.builder()
              .title("daile title")
              .content("daile content")
              .build();

      postRepository.save(post);

      PostEdit postEdit = PostEdit.builder()
              .content("변경된 내용")
              .build();


      //when
      postService.edit(post.getId(), postEdit);

      //then
      Post changedPost = postRepository.findById(post.getId())
              .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));
      assertEquals("daile title",changedPost.getTitle());
      assertEquals("변경된 내용",changedPost.getContent());
   }

   @Test
   @DisplayName("게시글 삭제")
   public void test6() throws Exception{
       //given
      Post post = Post.builder()
              .title("daile")
              .content("daile content")
              .build();
      postRepository.save(post);

       //when
      postService.delete(post.getId());

       //then
      assertEquals(0, postRepository.count());
   }

   @Test
   @DisplayName("글 1개 조회")
   public void test7() throws Exception{
      //given
      Post post = Post.builder()
              .title("foo")
              .content("bar")
              .build();
      postRepository.save(post);
      //post.getId() // primary_id = 1

      //expected
      assertThrows(PostNotFound.class,() -> {
         postService.get(post.getId()+1L);
      });
   }

   @Test
   @DisplayName("존재하지 않는 글 삭제")
   public void test8() throws Exception{

       //given
      Post post = Post.builder()
              .title("foo")
              .content("bar")
              .build();
      postRepository.save(post);
       //expected
      assertThrows(PostNotFound.class,()-> {
         postService.delete(post.getId()+1L);
      });
   }
   @Test
   @DisplayName("존재하지 않는 글 수정")
   public void test9() throws Exception{
      //given
      Post post = Post.builder()
              .title("daile title")
              .content("daile content")
              .build();

      postRepository.save(post);

      PostEdit postEdit = PostEdit.builder()
              .content("변경된 내용")
              .build();


      //expected
      assertThrows(PostNotFound.class,()-> {
         postService.edit(post.getId()+1L, postEdit);
      });
   }
}