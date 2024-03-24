package com.springboot.blog.service;

import com.springboot.blog.Payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long PostId, CommentDto commentDto);
    List<CommentDto> GetCommentsByPostId(long PostId);
    CommentDto getCommentById(Long PostId, Long CommentId);
}
