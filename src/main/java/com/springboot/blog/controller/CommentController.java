package com.springboot.blog.controller;

import com.springboot.blog.Payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{PostId}/comments")
    public ResponseEntity<CommentDto> CreateComment(@PathVariable(name = "PostId") long PostId, @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(PostId, commentDto), HttpStatus.CREATED);
    }
}
