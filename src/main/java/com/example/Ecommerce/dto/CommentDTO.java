
package com.example.Ecommerce.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Long postId;
    private Long userId;
    private String username;
}


