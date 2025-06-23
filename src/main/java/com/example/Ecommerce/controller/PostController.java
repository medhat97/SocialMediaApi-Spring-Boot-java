
package com.example.Ecommerce.controller;

import com.example.Ecommerce.dto.PostDTO;
import com.example.Ecommerce.entity.User;
import com.example.Ecommerce.service.AuthService;
import com.example.Ecommerce.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AuthService authService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO)
    {
        User currentUser = authService.getCurrentUser();
        return ResponseEntity.ok(postService.convertToDto(postService.createPost(postDTO, currentUser)));
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        User currentUser = authService.getCurrentUser();
        return ResponseEntity.ok(postService.convertToDto(postService.updatePost(id, postDTO, currentUser)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        User currentUser = authService.getCurrentUser();
        postService.deletePost(id, currentUser);
        return ResponseEntity.ok("Post deleted successfully!");
    }
}


