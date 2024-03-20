package com.springboot.blog.service;

import com.springboot.blog.Payload.PostDto;
import com.springboot.blog.Payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
//    List<PostDto> getAllPosts();
    PostResponse getAllPosts(int PageNo, int PageSize, String SortBy, String SortDir);
    PostDto getPostById(long id);
    PostDto UpdatePostById(PostDto postDto, long id);
    void deletePostById(long id);

}
