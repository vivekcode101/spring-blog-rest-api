package com.springboot.blog.service;

import com.springboot.blog.Payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long PostId, CommentDto commentDto);
}
