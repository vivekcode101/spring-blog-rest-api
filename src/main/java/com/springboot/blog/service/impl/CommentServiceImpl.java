package com.springboot.blog.service.impl;

import com.springboot.blog.Payload.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import jakarta.persistence.Id;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,
                              ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;

    }

    @Override
    public CommentDto createComment(long PostId, CommentDto commentDto) {
        Comment comment = MapToEntity(commentDto);
        //retrieve post by id
        Post post = postRepository.findById(PostId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", PostId));
        //set Post to comment Entity
        comment.setPost(post);
        //comment entity to database
        Comment newComment = commentRepository.save(comment);
        return MapToDto(newComment);
    }

    @Override
    public List<CommentDto> GetCommentsByPostId(long PostId) {
        // Retrieve Comments by PostId
        List<Comment> comments = commentRepository.findByPostId(PostId);
        // Convert list of comment entities to list of comment DTO's
        return comments.stream().map(comment -> MapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long PostId, Long CommentId) {
        //retrieve post entity by id
        Post post = postRepository.findById(PostId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", PostId));
        //retrieve comment entity by id
        Comment comment = commentRepository.findById(CommentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "Id", CommentId));
        if(!comment.getPost().getId().equals(post.getId()))
        {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");
        }
        return MapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long PostId, Long CommentId, CommentDto commentRequest) {
        //retrieve post entity by id
        Post post = postRepository.findById(PostId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", PostId));
        Comment comment = commentRepository.findById(CommentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "Id", CommentId));
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");

        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return MapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "Id", commentId));
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to the post");

        }
        commentRepository.delete(comment);
    }


    private CommentDto MapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment,CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment MapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return comment;
    }
}
