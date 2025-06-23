
package com.example.Ecommerce.repository;

import com.example.Ecommerce.entity.Like;
import com.example.Ecommerce.entity.Post;
import com.example.Ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostAndUser(Post post, User user);
    boolean existsByPostAndUser(Post post, User user);
}


