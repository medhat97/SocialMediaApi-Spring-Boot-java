
package com.example.Ecommerce.controller;

import com.example.Ecommerce.entity.Like;
import com.example.Ecommerce.entity.User;
import com.example.Ecommerce.service.AuthService;
import com.example.Ecommerce.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private AuthService authService;

    @PostMapping
    @PreAuthorize("hasRole(\'USER\") or hasRole(\'ADMIN\")")
    public ResponseEntity<String> toggleLike(@PathVariable Long postId) {
        User currentUser = authService.getCurrentUser();
        Like like = likeService.toggleLike(postId, currentUser);
        if (like == null) {
            return ResponseEntity.ok("Like removed successfully!");
        } else {
            return ResponseEntity.ok("Post liked successfully!");
        }
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole(\'USER\") or hasRole(\'ADMIN\")")
    public ResponseEntity<Boolean> getLikeStatus(@PathVariable Long postId) {
        User currentUser = authService.getCurrentUser();
        boolean hasLiked = likeService.hasUserLikedPost(postId, currentUser);
        return ResponseEntity.ok(hasLiked);
    }
}


