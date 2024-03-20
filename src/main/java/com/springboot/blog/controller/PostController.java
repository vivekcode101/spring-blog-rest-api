package com.springboot.blog.controller;

import com.springboot.blog.Payload.PostDto;
import com.springboot.blog.Payload.PostResponse;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post

        @PostMapping
        public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
            return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
        }
    
//      @GetMapping
//      public List<PostDto> getAllPosts(){
//          return postService.getAllPosts();
//        }

        @GetMapping
        public PostResponse getAllPosts(
                @RequestParam(value = "PageNo", defaultValue = "0", required = false) int PageNo,
                @RequestParam(value = "PageSize", defaultValue = "10", required = false) int PageSize,
                @RequestParam(value = "SortBy", defaultValue = "id", required = false) String SortBy

        ){
            return postService.getAllPosts(PageNo,PageSize,SortBy);
        }

        @GetMapping("/{id}")
        public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
            return  ResponseEntity.ok(postService.getPostById(id));
        }
        @PutMapping("/{id}")
        public ResponseEntity<PostDto> UpdatePostById(@RequestBody PostDto postDto, @PathVariable(name = "id") long id){
            PostDto postResponse = postService.UpdatePostById(postDto, id);
            return new ResponseEntity<>(postResponse, HttpStatus.OK);

        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deletePostById(@PathVariable(name = "id") long id){
            postService.deletePostById(id);
            return new ResponseEntity<>("Post Deleted Successfully.", HttpStatus.OK);
        }
}
