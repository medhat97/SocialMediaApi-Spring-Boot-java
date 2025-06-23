
package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.CommentDTO;
import com.example.Ecommerce.entity.Comment;
import com.example.Ecommerce.entity.Post;
import com.example.Ecommerce.entity.User;
import com.example.Ecommerce.repository.CommentRepository;
import com.example.Ecommerce.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public Comment createComment(Long postId, CommentDTO commentDTO, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setPost(post);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return post.getComments().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Comment updateComment(Long id, CommentDTO commentDTO, User currentUser) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to update this comment");
        }

        comment.setContent(commentDTO.getContent());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id, User currentUser) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to delete this comment");
        }
        commentRepository.delete(comment);
    }

    public CommentDTO convertToDto(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setPostId(comment.getPost().getId());
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setUsername(comment.getUser().getUsername());
        return commentDTO;
    }
}


