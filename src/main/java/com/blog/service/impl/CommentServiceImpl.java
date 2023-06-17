package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper=modelMapper;
    }

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id :" + postId)
        );
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment newcomment = commentRepository.save(comment);
        CommentDto dto = mapToDto(newcomment);
        return dto;
    }



    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("PostNotFoundWithId:"+postId)
        );
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> dto = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return dto;
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("PostNotFoundWithId:" + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment Not Found With Id:" + commentId)
        );

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("PostNotFoundWithId:" + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment Not Found With Id:" + commentId)
        );
       comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());

        Comment updatecomment = commentRepository.save(comment);
        CommentDto dto = mapToDto(updatecomment);

        return dto;
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("PostNotFoundWithId:" + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment Not Found With Id:" + commentId)
        );
        commentRepository.deleteById(commentId);
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        /*CommentDto commentdto = new CommentDto();
        commentdto.setId(comment.getId());
        commentdto.setBody(comment.getBody());
        commentdto.setName(comment.getName());
        commentdto.setEmail(comment.getEmail());*/

        return commentDto;
    }
   private Comment mapToEntity(CommentDto commentDto) {
       Comment comment = modelMapper.map(commentDto, Comment.class);
        /*Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setBody(commentDto.getBody());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());*/

        return comment;
    }
}
