package com.springboot.blog.service;

import com.springboot.blog.Payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();
    PostDto getPostById(long id);
    PostDto UpdatePostById(PostDto postDto, long id);
    void deletePostById(long id);
}
