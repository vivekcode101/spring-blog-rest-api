package com.springboot.blog.controller;

import com.springboot.blog.Payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{PostId}/comments")
    public ResponseEntity<CommentDto> CreateComment(@PathVariable(value = "PostId") long PostId, @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(PostId, commentDto), HttpStatus.CREATED);
    }
    @GetMapping("/posts/{PostId}/comments")
    public List<CommentDto> GetCommentsByPostId(@PathVariable(value = "PostId") Long PostId){
        return commentService.GetCommentsByPostId(PostId);
    }

    @GetMapping("/posts/{PostId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "PostId") Long PostId,
                                                     @PathVariable(value = "id") Long CommentId){
        CommentDto commentDto = commentService.getCommentById(PostId, CommentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }
}
