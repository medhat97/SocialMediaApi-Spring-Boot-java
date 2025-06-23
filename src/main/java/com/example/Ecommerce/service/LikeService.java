package com.example.Ecommerce.service;


import com.example.Ecommerce.entity.Like;
import com.example.Ecommerce.entity.Post;
import com.example.Ecommerce.entity.User;
import com.example.Ecommerce.repository.LikeRepository;
import com.example.Ecommerce.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    public Like toggleLike(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Optional<Like> existingLike = likeRepository.findByPostAndUser(post, user);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return null; // Indicates like was removed
        } else {
            Like like = new Like();
            like.setPost(post);
            like.setUser(user);
            like.setCreatedAt(LocalDateTime.now());
            return likeRepository.save(like);
        }
    }

    public boolean hasUserLikedPost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return likeRepository.existsByPostAndUser(post, user);
    }
}
