package com.pulsar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pulsar.model.Post;
import com.pulsar.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    // Method to find posts by user
    List<Post> findByUserOrderByCreatedAtDesc(User user);
    
    // Method to find posts by multiple users (for feed)
    List<Post> findByUserInOrderByCreatedAtDesc(List<User> users);
    
    // Method to find all posts ordered by creation date
    List<Post> findAllByOrderByCreatedAtDesc();
    
    // Method for search functionality
    List<Post> findByContentContainingIgnoreCase(String content);
    
    // Custom query for search
    @Query("SELECT p FROM Post p WHERE p.content LIKE %:query% OR p.user.username LIKE %:query% ORDER BY p.createdAt DESC")
    List<Post> searchByContentOrUsername(@Param("query") String query);
}