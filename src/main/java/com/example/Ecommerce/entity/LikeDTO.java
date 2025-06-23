
package com.example.Ecommerce.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LikeDTO {
    private Long id;
    private LocalDateTime createdAt;
    private Long postId;
    private Long userId;
    private String username;
}


