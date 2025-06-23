
package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.CommentDTO;
import com.example.Ecommerce.entity.User;
import com.example.Ecommerce.service.AuthService;
import com.example.Ecommerce.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AuthService authService;

    @PostMapping
    @PreAuthorize("hasRole(\'USER\') or hasRole(\'ADMIN\')")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @RequestBody CommentDTO commentDTO) {
        User currentUser = authService.getCurrentUser();
        return ResponseEntity.ok(commentService.convertToDto(commentService.createComment(postId, commentDTO, currentUser)));
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("hasRole(\'USER\') or hasRole(\'ADMIN\')")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        User currentUser = authService.getCurrentUser();
        return ResponseEntity.ok(commentService.convertToDto(commentService.updateComment(commentId, commentDTO, currentUser)));
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("hasRole(\'USER\') or hasRole(\'ADMIN\')")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        User currentUser = authService.getCurrentUser();
        commentService.deleteComment(commentId, currentUser);
        return ResponseEntity.ok("Comment deleted successfully!");
    }
}


