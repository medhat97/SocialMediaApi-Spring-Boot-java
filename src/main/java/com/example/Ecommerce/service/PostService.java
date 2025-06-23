
package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.PostDTO;
import com.example.Ecommerce.entity.Post;
import com.example.Ecommerce.entity.User;
import com.example.Ecommerce.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(PostDTO postDTO, User user) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);
        return postRepository.save(post);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return convertToDto(post);
    }

    public Post updatePost(Long id, PostDTO postDTO, User currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to update this post");
        }

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        return postRepository.save(post);
    }

    public void deletePost(Long id, User currentUser) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to delete this post");
        }
        postRepository.delete(post);
    }

    public PostDTO convertToDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setUserId(post.getUser().getId());
        postDTO.setUsername(post.getUser().getUsername());
        return postDTO;
    }
}


